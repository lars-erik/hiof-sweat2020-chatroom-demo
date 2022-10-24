package org.hiof.chatroom.core;

import org.approvaltests.Approvals;
import org.hiof.chatroom.commands.*;
import org.hiof.chatroom.notification.*;
import org.hiof.chatroom.persistence.*;
import org.hiof.chatroom.support.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.Instant;

public class When_sending_messages {
    PersistenceSupport persistenceSupport;
    NotificationService notificationService;

    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new PersistenceSupport();
    }

    @BeforeEach
    public void initialize_notification() {
        notificationService = Mockito.mock(NotificationService.class);

        NotificationServiceFactory.instance = new NotificationServiceFactory() {
            @Override
            public NotificationService getService() {
                return notificationService;
            }
        };
    }

    @AfterEach
    public void reset_time() {
        TimeFactory.reset();
    }

    @Test
    public void message_are_stored_in_database() throws Exception {
        TimeFactory.nowFactory = () -> Instant.parse("2020-10-01T23:59:59Z");

        SendMessageCommand cmd = sendMessage();
        ChatMessage last = getLastMessage();

        Approvals.verify(last);
    }

    @Test
    public void notification_is_sent_to_all_users() throws Exception {
        SendMessageCommand cmd = sendMessage();
        ChatMessage last = getLastMessage();

        Mockito.verify(notificationService).notifyNewMessage(last);
    }

    private ChatMessage getLastMessage() {
        ChatMessageRepository repo = persistenceSupport.getChatMessageRepository();
        ChatMessage last = repo.query().skip(repo.query().count() - 1).findFirst().get();
        return last;
    }

    private SendMessageCommand sendMessage() throws Exception {
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke Skywalker";
        cmd.message = "This is Red Leader. We're approaching the Ison Corridor!";
        cmd.execute();
        return cmd;
    }
}
