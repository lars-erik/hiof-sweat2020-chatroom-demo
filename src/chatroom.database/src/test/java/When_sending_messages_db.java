import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.core.When_sending_messages;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.UnitOfWork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class When_sending_messages_db extends When_sending_messages {

    @Override
    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new DbPersistenceSupport();
    }
}
