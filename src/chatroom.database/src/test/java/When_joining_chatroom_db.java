import org.hiof.chatroom.core.When_joining_chatroom;
import org.junit.jupiter.api.BeforeEach;

public class When_joining_chatroom_db extends When_joining_chatroom {

    @Override
    @BeforeEach
    public void initialize_persistence() throws Exception {
        persistenceSupport = new DbPersistenceSupport();
    }
}
