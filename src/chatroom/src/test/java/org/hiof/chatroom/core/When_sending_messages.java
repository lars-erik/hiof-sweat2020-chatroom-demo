package org.hiof.chatroom.core;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.commands.SendMessageCommandHandler;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.support.PersistenceSupport;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.Instant;

public class When_sending_messages {
    protected PersistenceSupport persistenceSupport;
    protected NotificationService notificationService;

    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new PersistenceSupport();
    }

    @BeforeEach
    public void initialize_notification() {
        notificationService = Mockito.mock(NotificationService.class);
    }

    @BeforeEach
    public void initialize_time() {
        TimeFactory.nowFactory = () -> Instant.parse("2020-10-01T23:59:59Z");
    }

    @AfterEach
    public void reset_time() {
        TimeFactory.reset();
    }

    @AfterEach
    public void cleanup_database() throws Exception {
        persistenceSupport.cleanup();
    }

    @Test
    public void message_are_stored_in_database() throws Exception {
        SendMessageCommand cmd = sendMessage();
        ChatMessage last = getLastMessage();

        Approvals.verify(last);
    }

    @Test
    public void notification_is_sent_to_all_users() throws Exception {
        SendMessageCommand cmd = sendMessage();
        ChatMessage last = getLastMessage();

        Mockito.verify(notificationService).notifyNewMessage(Mockito.argThat(x -> last.toString().equals(x.toString())));
    }

    private ChatMessage getLastMessage() {
        Repository<ChatMessage> repo = persistenceSupport.getChatMessageRepository();
        ChatMessage last = repo.query().sortedDescendingBy(x -> x.getTime()).findFirst().get();
        return last;
    }

    protected SendMessageCommand sendMessage() throws Exception {
        SendMessageCommand cmd = createCommand();

        SendMessageCommandHandler handler = new SendMessageCommandHandler(
            persistenceSupport.getChatMessageRepository(),
            persistenceSupport.getUnitOfWork(),
            notificationService
        );
        handler.execute(cmd);

        return cmd;
    }

    protected SendMessageCommand createCommand() {
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke Skywalker";
        cmd.message = "This is Red Leader. We're approaching the Ison Corridor!";
        return cmd;
    }
}
