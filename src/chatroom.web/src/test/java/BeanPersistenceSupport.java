import org.hibernate.query.Query;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.support.PersistenceSupport;
import org.springframework.context.support.GenericApplicationContext;

public class BeanPersistenceSupport extends PersistenceSupport {
    private final GenericApplicationContext ctx;

    public BeanPersistenceSupport(GenericApplicationContext ctx) {
        this.ctx = ctx;
        setUow(ctx.getBean(UnitOfWork.class));
    }

    @Override
    public UnitOfWork getUnitOfWork() {
        return ctx.getBean(UnitOfWork.class);
    }

    @Override
    public Repository<ChatMessage> getChatMessageRepository() {
        return ctx.getBean(ChatMessageRepository.class);
    }

    @Override
    public void cleanup() throws Exception {
        super.cleanup();

        DbPersistenceSupport.emptyDatabase();
    }
}
