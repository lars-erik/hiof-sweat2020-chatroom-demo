import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.commands.SendMessageCommandHandler;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.UnitOfWork;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.queries.LastMessagesQueryHandler;
import org.hiof.chatroom.web.ChatUIController;
import org.hiof.chatroom.web.CommandDispatcher;
import org.hiof.chatroom.web.NotificationDispatcher;
import org.hiof.chatroom.web.QueryDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.File;

public class Persisting_in_web_module {

    @BeforeEach
    public void reset_database() throws Exception {
        DbPersistenceSupport.emptyDatabase();
    }

    @Test
    public void stores_message() throws Exception {
        NotificationService notificationService = message -> {};

        final UnitOfWork uow = new org.hiof.chatroom.database.UnitOfWork();
        final Repository<ChatMessage> repo = new org.hiof.chatroom.database.ChatMessageRepository(uow);

        ChatUIController ctrlr = new ChatUIController(
                new NotificationDispatcher(new SimpMessagingTemplate((message, l) -> true)),
                new QueryDispatcher((x) -> new LastMessagesQueryHandler(repo)),
                new CommandDispatcher((x) -> new SendMessageCommandHandler(repo, uow, notificationService))
        );

        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke";
        cmd.message = "Noooo";
        ctrlr.postMessage(cmd);

        final UnitOfWork uow2 = new org.hiof.chatroom.database.UnitOfWork();
        final Repository<ChatMessage> repo2 = new org.hiof.chatroom.database.ChatMessageRepository(uow2);

        ChatMessage msg = repo2.query().findFirst().get();
        Assertions.assertEquals("Luke", msg.getUser());
        Assertions.assertEquals("Noooo", msg.getMessage());
    }
}
