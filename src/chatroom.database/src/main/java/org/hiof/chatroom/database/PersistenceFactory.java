package org.hiof.chatroom.database;

import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.UnitOfWork;

public class PersistenceFactory extends org.hiof.chatroom.persistence.PersistenceFactory {
    @Override
    public UnitOfWork createUnitOfWork() throws Exception {
        return new org.hiof.chatroom.database.UnitOfWork();
    }

    @Override
    public ChatMessageRepository createChatMessageRepository(UnitOfWork uow) {
        return new org.hiof.chatroom.database.ChatMessageRepository((org.hiof.chatroom.database.UnitOfWork)uow);
    }
}
