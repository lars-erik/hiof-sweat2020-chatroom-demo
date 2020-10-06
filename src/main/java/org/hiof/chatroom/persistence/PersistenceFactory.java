package org.hiof.chatroom.persistence;

public abstract class PersistenceFactory {
    public PersistenceFactory Instance;

    public abstract UnitOfWork createUnitOfWork();
    public abstract ChatMessageRepository createChatMessageRepository(UnitOfWork uow);
}
