package org.hiof.chatroom.support;

import org.hiof.chatroom.fakes.FakePersistenceFactory;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.UnitOfWork;

public class PersistenceSupport {
    protected PersistenceFactory factory;
    protected UnitOfWork uow;
    protected ChatMessageRepository repo;

    public PersistenceSupport() throws Exception {
        factory = new FakePersistenceFactory();
        setUow(factory.createUnitOfWork());
        repo = factory.createChatMessageRepository(getUnitOfWork());

        PersistenceFactory.Instance = factory;
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
