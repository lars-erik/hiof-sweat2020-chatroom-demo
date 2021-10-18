package org.hiof.chatroom.database;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;

public class PersistenceFactory extends org.hiof.chatroom.persistence.PersistenceFactory {
    @Override
    public UnitOfWork createUnitOfWork() throws Exception {
        return new org.hiof.chatroom.database.UnitOfWork();
    }

    @Override
    public Repository<ChatMessage> createChatMessageRepository(UnitOfWork uow) {
        return new org.hiof.chatroom.database.ChatMessageRepository((org.hiof.chatroom.database.UnitOfWork)uow);
    }
}
