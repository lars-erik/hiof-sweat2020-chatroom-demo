package org.hiof.chatroom.support;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.fakes.FakeNewMessagesRepoQueryHandler;
import org.hiof.chatroom.fakes.FakePersistenceFactory;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.queries.NewMessagesQuery;

public class PersistenceSupport {
    protected PersistenceFactory factory;
    protected UnitOfWork uow;
    protected Repository<ChatMessage> repo;

    public PersistenceSupport() throws Exception {
        factory = new FakePersistenceFactory();
        setUow(factory.createUnitOfWork());
        repo = factory.createChatMessageRepository(getUnitOfWork());

        PersistenceFactory.Instance = factory;

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

    public void cleanup() {
        uow.close();
    }
}
