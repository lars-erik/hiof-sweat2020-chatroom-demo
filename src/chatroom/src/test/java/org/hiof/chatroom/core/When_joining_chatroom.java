package org.hiof.chatroom.core;

import org.approvaltests.Approvals;
import org.hiof.chatroom.persistence.*;
import org.hiof.chatroom.queries.*;
import org.hiof.chatroom.support.*;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.*;

public class When_joining_chatroom {
    protected PersistenceSupport persistenceSupport;

    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new PersistenceSupport();
    }

    @AfterEach
    public void cleanup_database() throws Exception {
        persistenceSupport.cleanup();
    }

    @Test
    public void last_20_messages_are_shown() throws Exception {
        UnitOfWork uow = persistenceSupport.getUnitOfWork();
        try {
            Repository<ChatMessage> repo = persistenceSupport.getChatMessageRepository();

            List<String> expectedMessages = new ArrayList<>();
            for (int i = 0; i < 21; i++) {
                final String timeStamp = "2020-10-01T23:00:" + String.format("%02d", i) + "Z";
                ChatMessage msg = new ChatMessage(UUID.randomUUID(), Instant.parse(timeStamp), "Luke", "Nooo! " + i);
                expectedMessages.add(msg.toString());
                repo.add(msg);
            }
            uow.saveChanges();
            Collections.reverse(expectedMessages);

            LastMessagesQuery query = new LastMessagesQuery(20);

            List<String> lastMessages = (List<String>)execute(repo, query);
            Approvals.verifyAll("Messages", lastMessages);
        }
        finally {
            uow.close();
        }
    }

    protected Object execute(Repository<ChatMessage> repo, LastMessagesQuery query) throws Exception {
        LastMessagesQueryHandler handler = new LastMessagesQueryHandler(repo);
        List<String> lastMessages = (List<String>)handler.execute(query);
        return lastMessages;
    }
}
