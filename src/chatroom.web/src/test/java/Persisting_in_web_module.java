import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.PersistenceFactory;
import org.hiof.chatroom.database.UnitOfWork;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.web.ChatUIController;
import org.hiof.chatroom.web.NotificationDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

public class Persisting_in_web_module {

    @BeforeEach
    public void reset_database() {
        java.io.File dbFile = new File("./db/chat.db");
        dbFile.delete();
    }

    @Test
    public void stores_message() throws Exception {
        PersistenceFactory.Instance = new PersistenceFactory();
        NotificationServiceFactory.Instance = new NotificationServiceFactory() {
            @Override
            public NotificationService getService() {
                return new NotificationService() {
                    @Override
                    public void notifyNewMessage(ChatMessage message) {
                    }
                };
            }
        };

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

        fail("Gidder ikke endre ctor i ett sett");

        /*

        ChatUIController ctrlr = new ChatUIController(new NotificationDispatcher(new SimpMessagingTemplate(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long l) {
                return true;
            }
        })), NotificationServiceFactory.Instance.getService()); //

        ctrlr.postMessage("Luke", "Noooo");

        org.hiof.chatroom.persistence.UnitOfWork uow = PersistenceFactory.Instance.createUnitOfWork();
        org.hiof.chatroom.persistence.Repository<ChatMessage> repo = PersistenceFactory.Instance.createChatMessageRepository(uow);

        ChatMessage msg = repo.all().findFirst().get();
        Assertions.assertEquals("Luke", msg.getUser());
        Assertions.assertEquals("Noooo", msg.getMessage());


         */
    }
}
