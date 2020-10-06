package org.hiof.chatroom.core;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.support.PersistenceSupport;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class When_sending_messages {
    PersistenceSupport persistenceSupport;
    NotificationService notificationService;

    @BeforeEach
    public void initialize_persistence() {
        persistenceSupport = new PersistenceSupport();
    }

    @BeforeEach
    public void initialize_notification() {
        notificationService = Mockito.mock(NotificationService.class);

        NotificationServiceFactory.Instance = new NotificationServiceFactory() {
            @Override
            public NotificationService getService() {
                return notificationService;
            }
        };
    }

    @Test
    public void message_are_stored_in_database() {
        SendMessageCommand cmd = sendMessage();

        ChatMessageRepository repo = persistenceSupport.getChatMessageRepository();
        ChatMessage last = repo.query().skip(repo.query().count() - 1).findFirst().get();

        Assertions.assertEquals(cmd.user, last.getUser());
        Assertions.assertEquals(cmd.message, last.getMessage());
    }

    @Test
    public void notification_is_sent_to_all_users() {
        SendMessageCommand cmd = sendMessage();

        Mockito.verify(notificationService).notifyNewMessages();
    }

    private SendMessageCommand sendMessage() {
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke Skywalker";
        cmd.message = "This is Red Leader. We're approaching the Ison Corridor!";
        cmd.execute();
        return cmd;
    }
}
