package org.hiof.chatroom.core;

import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.hiof.chatroom.queries.NewMessagesQueryHandler;
import org.hiof.chatroom.support.PersistenceSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class When_joining_chatroom {
    protected PersistenceSupport persistenceSupport;

    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new PersistenceSupport();
    }

    @Test
    public void last_20_messages_are_shown() throws Exception {
        UnitOfWork uow = persistenceSupport.getUnitOfWork();
        try {
            Repository<ChatMessage> repo = persistenceSupport.getChatMessageRepository();

            List<String> expectedMessages = new ArrayList<>();
            for (int i = 0; i < 21; i++) {
                final String timeStamp = "2020-10-01T23:00:" + String.format("%02d", i) + "Z";
                ChatMessage msg = new ChatMessage(UUID.randomUUID().toString(), Instant.parse(timeStamp), "Luke", "Nooo! " + i);
                expectedMessages.add(msg.toString());
                repo.add(msg);
            }
            uow.saveChanges();

            NewMessagesQuery query = new NewMessagesQuery(20);
            NewMessagesQueryHandler handler = new NewMessagesQueryHandler(repo, uow);

            List<String> lastMessages = (List<String>)handler.execute(query);

            Collections.reverse(expectedMessages);
            Assertions.assertArrayEquals(expectedMessages.stream().limit(20).toArray(), lastMessages.toArray());
        }
        finally {
            uow.close();
        }
    }
}
