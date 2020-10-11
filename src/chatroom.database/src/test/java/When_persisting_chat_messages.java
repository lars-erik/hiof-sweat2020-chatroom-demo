import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.UnitOfWork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class When_persisting_chat_messages {
    @Test
    public void stores_message() throws Exception {
        DatabaseManager.ensureDatabase("./db/chat-test.db", true);
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
        Assertions.assertNotNull(msg);
    }
}
