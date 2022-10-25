import org.hiof.chatroom.core.When_joining_chatroom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class When_joining_chatroom_db extends When_joining_chatroom {

    @Override
    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new DbPersistenceSupport();
    }

    @Test
    @Override
    public void last_20_messages_are_shown() throws Exception {
        super.last_20_messages_are_shown();
    }
}
