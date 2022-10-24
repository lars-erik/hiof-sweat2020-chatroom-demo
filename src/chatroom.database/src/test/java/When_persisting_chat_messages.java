import org.approvaltests.Approvals;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.*;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.UUID;

public class When_persisting_chat_messages {
    @Test
    public void stores_message() throws Exception {
        DatabaseManager.ensureDatabase("./db/chat-test.db");
        UnitOfWork uow = new UnitOfWork();
        ChatMessageRepository repo = new ChatMessageRepository(uow);
        UUID id = UUID.fromString("70f49e80-bf70-44bb-8179-b3f2a1357a11");
        ChatMessage msg = new ChatMessage(
            id, Instant.parse("2022-10-15T12:00:00Z"),
            "Luke", "This is red leader..."
        );
        repo.add(msg);
        uow.saveChanges();
        uow.close();
        uow = new UnitOfWork();
        repo = new ChatMessageRepository(uow);
        msg = repo.get(id);
        Approvals.verify(msg);
    }
}
