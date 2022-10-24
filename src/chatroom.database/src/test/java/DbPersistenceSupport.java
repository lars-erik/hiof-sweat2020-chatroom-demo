import org.hibernate.Session;
import org.hiof.chatroom.database.UnitOfWork;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.support.PersistenceSupport;

public class DbPersistenceSupport extends PersistenceSupport {

    public DbPersistenceSupport() throws Exception {
        factory = new org.hiof.chatroom.database.PersistenceFactory();

        uow = factory.createUnitOfWork();
        Session session = ((UnitOfWork) uow).getSession();
        session.createSQLQuery("DELETE FROM ChatMessages").executeUpdate();
        session.flush();
        uow.close();

        uow = factory.createUnitOfWork();
        repo = factory.createChatMessageRepository(getUnitOfWork());


        PersistenceFactory.instance = factory;
    }
}
