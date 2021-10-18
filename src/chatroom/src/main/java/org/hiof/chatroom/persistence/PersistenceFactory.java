package org.hiof.chatroom.persistence;

import org.hiof.chatroom.core.ChatMessage;

public abstract class PersistenceFactory {
    public static PersistenceFactory Instance;

    public abstract UnitOfWork createUnitOfWork() throws Exception;
    public abstract Repository<ChatMessage> createChatMessageRepository(UnitOfWork uow);
}
