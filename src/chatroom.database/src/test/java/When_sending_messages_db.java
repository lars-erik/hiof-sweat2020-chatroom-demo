import org.hiof.chatroom.core.When_sending_messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class When_sending_messages_db extends When_sending_messages {

    @Override
    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new DbPersistenceSupport();
    }

    @Test
    @Override
    public void message_are_stored_in_database() throws Exception {
        super.message_are_stored_in_database();
    }

}
