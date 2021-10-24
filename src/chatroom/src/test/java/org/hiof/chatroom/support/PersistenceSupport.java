package org.hiof.chatroom.support;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.fakes.FakeChatMessageRepository;
import org.hiof.chatroom.fakes.FakeNewMessagesRepoQueryHandler;
import org.hiof.chatroom.fakes.FakeUnitOfWork;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.queries.NewMessagesQuery;

public class PersistenceSupport {
    protected UnitOfWork uow;
    protected Repository<ChatMessage> repo;

    public PersistenceSupport() {
        setUow(new FakeUnitOfWork());
        repo = new FakeChatMessageRepository();

        RepositoryQueryHandlerFactory.register(NewMessagesQuery.class, FakeNewMessagesRepoQueryHandler.class);
    }

    public Repository<ChatMessage> getChatMessageRepository() {
        return repo;
    }

    public UnitOfWork getUnitOfWork() {
        return uow;
    }

    public void setUow(UnitOfWork uow) {
        this.uow = uow;
    }

    public void cleanup() throws Exception {
        uow.close();
    }
}
