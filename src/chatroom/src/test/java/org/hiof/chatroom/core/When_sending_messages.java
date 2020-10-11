package org.hiof.chatroom.core;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.support.PersistenceSupport;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.Instant;

public class When_sending_messages {
    protected PersistenceSupport persistenceSupport;
    NotificationService notificationService;

    @BeforeEach
    public void initialize_persistence() throws Exception {
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

    @AfterEach
    public void reset_time() {
        TimeFactory.reset();
    }

    @AfterEach
    public void cleanup_database() {
        persistenceSupport.cleanup();
    }

    @Test
    public void message_are_stored_in_database() throws Exception {
        SendMessageCommand cmd = sendMessage();
        ChatMessage last = getLastMessage();

        Assertions.assertEquals(cmd.user, last.getUser());
        Assertions.assertEquals(cmd.message, last.getMessage());
    }

    @Test
    public void message_are_stamped_with_time() throws Exception {
        TimeFactory.nowFactory = () -> Instant.parse("2020-10-01T23:59:59Z");

        SendMessageCommand cmd = sendMessage();

        persistenceSupport.cleanup();
        initialize_persistence();

        ChatMessage last = getLastMessage();

        Assertions.assertEquals("2020-10-01T23:59:59Z", last.getTime().toString());
    }

    @Test
    public void notification_is_sent_to_all_users() throws Exception {
        SendMessageCommand cmd = sendMessage();
        ChatMessage last = getLastMessage();
        String expected = last.toString();

        Mockito.verify(notificationService).notifyNewMessage(Mockito.argThat(x -> expected.equals(x.toString())));
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
