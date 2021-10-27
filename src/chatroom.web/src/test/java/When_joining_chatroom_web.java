import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.core.When_joining_chatroom;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.hiof.chatroom.web.ChatUIController;
import org.hiof.chatroom.web.NotificationDispatcher;
import org.hiof.chatroom.web.WebApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@SpringBootTest(classes=When_joining_chatroom_web.class)
@ExtendWith(SpringExtension.class)
public class When_joining_chatroom_web extends When_joining_chatroom {

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
    protected Object execute(UnitOfWork uow, Repository<ChatMessage> repo, NewMessagesQuery query) {
        ChatUIController controller = ctx.getBean(ChatUIController.class);
        Model model = new ConcurrentModel();
        controller.chatUI(model);

        return model.getAttribute("log");
    }
}
