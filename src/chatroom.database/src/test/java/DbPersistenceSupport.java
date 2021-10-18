import org.hibernate.query.Query;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.UnitOfWork;
import org.hiof.chatroom.database.queryhandlers.NewMessagesDbQueryHandler;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.hiof.chatroom.support.PersistenceSupport;

public class DbPersistenceSupport extends PersistenceSupport {

    public DbPersistenceSupport() throws Exception {
        factory = new org.hiof.chatroom.database.PersistenceFactory();
        setUow(factory.createUnitOfWork());
        repo = factory.createChatMessageRepository(getUnitOfWork());

        RepositoryQueryHandlerFactory.register(NewMessagesQuery.class, NewMessagesDbQueryHandler.class);
    }

    @Override
    public org.hiof.chatroom.persistence.UnitOfWork getUnitOfWork() {
        if (((UnitOfWork)uow).isClosed()) {
            try {
                setUow(factory.createUnitOfWork());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return uow;
    }

    @Override
    public Repository<ChatMessage> getChatMessageRepository() {
        if (((UnitOfWork)uow).isClosed()) {
            return repo = factory.createChatMessageRepository(getUnitOfWork());
        }
        return repo;
    }

    @Override
    public void cleanup() throws Exception {
        super.cleanup();

        // Warning: Will not cascade delete!
        UnitOfWork newUow = (UnitOfWork) factory.createUnitOfWork();
        Query query = newUow.getSession().createQuery("DELETE FROM ChatMessage");
        query.executeUpdate();
        newUow.saveChanges();
        newUow.close();
    }
}
