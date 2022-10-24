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
        uow = factory.createUnitOfWork();
        repo = factory.createChatMessageRepository(uow);

        PersistenceFactory.instance = factory;
    }

    public UnitOfWork getUnitOfWork() { return uow; }
    public ChatMessageRepository getChatMessageRepository() {
        return repo;
    }

    public void cleanup() {
        uow.close();
    }
}
