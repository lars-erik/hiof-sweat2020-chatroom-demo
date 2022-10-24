package org.hiof.chatroom.core;

import org.approvaltests.Approvals;
import org.hiof.chatroom.persistence.*;
import org.hiof.chatroom.queries.*;
import org.hiof.chatroom.support.*;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.*;

public class When_joining_chatroom {
    PersistenceSupport persistenceSupport;

    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new PersistenceSupport();
    }

    @Test
    public void last_20_messages_are_shown() throws Exception {
        UnitOfWork uow = persistenceSupport.getUnitOfWork();
        try {
            ChatMessageRepository repo = persistenceSupport.getChatMessageRepository();

            for (int i = 0; i < 21; i++) {
                final String timeStamp = "2020-10-01T23:00:" + String.format("%02d", i) + "Z";
                TimeFactory.nowFactory = () -> Instant.parse(timeStamp);
                ChatMessage msg = new ChatMessage(UUID.randomUUID(), TimeFactory.now(), "Luke", "Nooo! " + i);
                repo.add(msg);
            }
            uow.saveChanges();

            List<String> lastMessages = new LastMessagesQuery().execute();
            Approvals.verifyAll("Messages", lastMessages);
        }
        finally {
            uow.close();
        }
    }

}
