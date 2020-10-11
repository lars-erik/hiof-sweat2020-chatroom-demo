package org.hiof.chatroom.core;

import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.support.PersistenceSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

            List<ChatMessage> messages = new ArrayList<>();
            for (int i = 0; i < 21; i++) {
                final String timeStamp = "2020-10-01T23:00:" + String.format("00", i) + "Z";
                TimeFactory.nowFactory = () -> Instant.parse(timeStamp);
                ChatMessage msg = new ChatMessage(UUID.randomUUID().toString(), TimeFactory.now(), "Luke", "Nooo! " + i);
                messages.add(msg);
                repo.add(msg);
            }
            uow.saveChanges();

            List<String> lastMessages = repo.getLastMessages();
            Assertions.assertArrayEquals(messages.stream().skip(1).toArray(), lastMessages.toArray());
        }
        finally {
            uow.close();
        }
    }
}
