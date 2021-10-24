import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.commands.SendMessageCommandHandler;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.UnitOfWork;
import org.hiof.chatroom.fakes.FakeChatMessageRepository;
import org.hiof.chatroom.fakes.FakeUnitOfWork;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.queries.NewMessagesQueryHandler;
import org.hiof.chatroom.web.ChatUIController;
import org.hiof.chatroom.web.CommandDispatcher;
import org.hiof.chatroom.web.NotificationDispatcher;
import org.hiof.chatroom.web.QueryDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class Persisting_in_web_module {

    private Model model;

    @BeforeEach
    public void reset_database() {
        DatabaseManager.ensureDatabase("./db/chat.db", true);

        model = createModel();
    }

    @Test
    public void stores_message() throws Exception {
        NotificationService notificationService = message -> {};

        final UnitOfWork uow = new org.hiof.chatroom.database.UnitOfWork();
        final Repository<ChatMessage> repo = new org.hiof.chatroom.database.ChatMessageRepository(uow);

        ChatUIController ctrlr = new ChatUIController(
                new NotificationDispatcher(new SimpMessagingTemplate((message, l) -> true)),
                new QueryDispatcher((x) -> new NewMessagesQueryHandler(repo, uow)),
                new CommandDispatcher((x) -> new SendMessageCommandHandler(repo, uow, notificationService))
        );

        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke";
        cmd.message = "Noooo";
        ctrlr.postMessage(cmd);

        final UnitOfWork uow2 = new org.hiof.chatroom.database.UnitOfWork();
        final Repository<ChatMessage> repo2 = new org.hiof.chatroom.database.ChatMessageRepository(uow2);

        ChatMessage msg = repo2.all().findFirst().get();
        Assertions.assertEquals("Luke", msg.getUser());
        Assertions.assertEquals("Noooo", msg.getMessage());
    }

    private Model createModel() {
        Model model = new Model() {
            @Override
            public Model addAttribute(String s, Object o) {
                return null;
            }

            @Override
            public Model addAttribute(Object o) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> collection) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public boolean containsAttribute(String s) {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };
        return model;
    }
}
