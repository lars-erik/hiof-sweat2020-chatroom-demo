import org.hibernate.query.Query;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.UnitOfWork;
import org.hiof.chatroom.database.queryhandlers.DbNewMessagesHandler;
import org.hiof.chatroom.fakes.FakeNewMessagesHandler;
import org.hiof.chatroom.fakes.FakePersistenceFactory;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.QueryHandlerFactory;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.hiof.chatroom.support.PersistenceSupport;

public class DbPersistenceSupport extends PersistenceSupport {

    public DbPersistenceSupport() throws Exception {
        //DatabaseManager.ensureDatabase("./db/chat-test.db", true);

        factory = new org.hiof.chatroom.database.PersistenceFactory();
        setUow(factory.createUnitOfWork());
        repo = factory.createChatMessageRepository(getUnitOfWork());

        PersistenceFactory.Instance = factory;

        QueryHandlerFactory.register(NewMessagesQuery.class, DbNewMessagesHandler.class);

    }

    @Override
    public void cleanup()
    {
        // Warning: Will not cascade delete!
        Query query = ((UnitOfWork)uow).getSession().createQuery("DELETE FROM ChatMessage");
        query.executeUpdate();
        uow.saveChanges();

        super.cleanup();
    }
}
