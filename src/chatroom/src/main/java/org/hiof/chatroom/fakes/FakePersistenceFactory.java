package org.hiof.chatroom.fakes;

import org.hiof.chatroom.persistence.*;

public class FakePersistenceFactory extends PersistenceFactory {
    UnitOfWork uow;
    ChatMessageRepository chatMessageRepository;

    @Override
    public UnitOfWork createUnitOfWork() {
        if (uow == null) {
            uow = new FakeUnitOfWork();
        }
        return uow;
    }

    @Override
    public ChatMessageRepository createChatMessageRepository(UnitOfWork uow) {
        if (chatMessageRepository == null) {
            chatMessageRepository = new FakeChatMessageRepository();
        }
        return chatMessageRepository;
    }
}
