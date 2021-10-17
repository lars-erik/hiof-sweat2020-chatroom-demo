package org.hiof.chatroom.support;

import org.hiof.chatroom.fakes.FakeNewMessagesHandler;
import org.hiof.chatroom.fakes.FakePersistenceFactory;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.QueryHandlerFactory;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.queries.NewMessagesQuery;

public class PersistenceSupport {
    protected PersistenceFactory factory;
    protected UnitOfWork uow;
    protected ChatMessageRepository repo;

    public PersistenceSupport() throws Exception {
        factory = new FakePersistenceFactory();
        setUow(factory.createUnitOfWork());
        repo = factory.createChatMessageRepository(getUnitOfWork());

        PersistenceFactory.Instance = factory;

        QueryHandlerFactory.register(NewMessagesQuery.class, FakeNewMessagesHandler.class);

    }

    public ChatMessageRepository getChatMessageRepository() {
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
