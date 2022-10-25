import org.hibernate.query.Query;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.database.UnitOfWork;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.support.PersistenceSupport;

public class DbPersistenceSupport extends PersistenceSupport {

    public DbPersistenceSupport() throws Exception {
        UnitOfWork uow = new UnitOfWork();
        setUow(uow);
        repo = new ChatMessageRepository(uow);
    }

    @Override
    public org.hiof.chatroom.persistence.UnitOfWork getUnitOfWork() {
        if (((UnitOfWork)uow).isClosed()) {
            try {
                setUow(new UnitOfWork());
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
            return repo = new ChatMessageRepository((UnitOfWork) getUnitOfWork());
        }
        return repo;
    }

    @Override
    public void cleanup() throws Exception {
        super.cleanup();

        emptyDatabase();
    }

    public static void emptyDatabase() throws Exception {
        UnitOfWork newUow = new UnitOfWork();
        Query query = newUow.getSession().createSQLQuery("DELETE FROM ChatMessages");
        query.executeUpdate();
        newUow.saveChanges();
        newUow.close();
    }
}
