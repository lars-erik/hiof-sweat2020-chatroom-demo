import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.support.PersistenceSupport;

public class DbPersistenceSupport extends PersistenceSupport {

    public DbPersistenceSupport() throws Exception {
        factory = new org.hiof.chatroom.database.PersistenceFactory();
        uow = factory.createUnitOfWork();
        repo = factory.createChatMessageRepository(getUnitOfWork());

        PersistenceFactory.instance = factory;
    }
}
