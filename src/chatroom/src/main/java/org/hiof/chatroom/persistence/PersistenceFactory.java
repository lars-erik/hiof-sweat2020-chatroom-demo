package org.hiof.chatroom.persistence;

public abstract class PersistenceFactory {
    public static PersistenceFactory instance;

    public abstract UnitOfWork createUnitOfWork();
    public abstract ChatMessageRepository createChatMessageRepository(UnitOfWork uow);
}
