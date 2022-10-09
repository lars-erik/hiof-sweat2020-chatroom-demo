import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.UnitOfWork;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class When_persisting_chat_messages {
    @Test
    public void stores_message() throws Exception {
        DatabaseManager.ensureDatabase("./db/chat-test.db");
        UnitOfWork uow = new UnitOfWork();
        ChatMessageRepository repo = new ChatMessageRepository(uow);
        ChatMessage msg = new ChatMessage();
        String id = UUID.randomUUID().toString();
        msg.setId(id);
        repo.add(msg);
        uow.saveChanges();
        uow.close();
        uow = new UnitOfWork();
        repo = new ChatMessageRepository(uow);
        msg = repo.get(id);
        assertNotNull(msg);
    }

    @Test
    public void translates_lambda_via_jinq() throws Exception {
        DatabaseManager.ensureDatabase("./db/chat-test.db");
        UnitOfWork uow = new UnitOfWork();
        ChatMessageRepository repo = new ChatMessageRepository(uow);
        for(int i = 0; i<10; i++) {
            ChatMessage msg = new ChatMessage(UUID.randomUUID().toString(), "Luke", "Noooo");
            repo.add(msg);
        }
        uow.saveChanges();

        PrintStream oldOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        List<ChatMessage> list = repo.query().limit(5).collect(Collectors.toList());
        assertEquals(5, list.size());

        System.setOut(oldOut);

        assertEquals("Hibernate: \n" +
                "    select\n" +
                "        chatmessag0_.Id as id1_0_,\n" +
                "        chatmessag0_.UserName as username2_0_,\n" +
                "        chatmessag0_.Message as message3_0_ \n" +
                "    from\n" +
                "        ChatMessages chatmessag0_ limit ?\n", out.toString().replace("\r\n", "\n"));
    }
}
