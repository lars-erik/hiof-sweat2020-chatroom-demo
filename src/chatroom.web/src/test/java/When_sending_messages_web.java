import org.hibernate.query.Query;
import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.core.When_sending_messages;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.queryhandlers.NewMessagesDbQueryHandler;
import org.hiof.chatroom.fakes.FakeNewMessagesRepoQueryHandler;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.hiof.chatroom.support.PersistenceSupport;
import org.hiof.chatroom.web.ChatUIController;
import org.hiof.chatroom.web.NotificationDispatcher;
import org.hiof.chatroom.web.WebApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes=When_sending_messages_web.class)
@ExtendWith(SpringExtension.class)
public class When_sending_messages_web extends When_sending_messages {

    private GenericApplicationContext ctx;

    @BeforeEach
    public void reset_database() {
        DatabaseManager.ensureDatabase("./db/chat.db", true);
    }

    @Override
    @BeforeEach
    public void initialize_persistence() throws Exception {
        ctx = new GenericApplicationContext();
        WebApplication.configureFactories();
        WebApplication.configureExceptNotificationService(ctx, "singleton");

        ctx.registerBean(ChatUIController.class);
        ctx.registerBean(NotificationDispatcher.class, () -> new NotificationDispatcher(new SimpMessagingTemplate((message, l) -> true)));

        ctx.refresh();

        persistenceSupport = new BeanPersistenceSupport(ctx);
    }

    @Override
    @BeforeEach
    public void initialize_notification() {

        super.initialize_notification();
        ctx.registerBean(NotificationService.class, () -> notificationService);
    }

    @Override
    protected SendMessageCommand sendMessage() {
        ChatUIController controller = ctx.getBean(ChatUIController.class);
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke";
        cmd.message = "I'm going in";
        try {
            controller.postMessage(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmd;
    }
}

