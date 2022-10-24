package org.hiof.chatroom.database;

import org.hiof.chatroom.core.ChatMessage;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.orm.stream.JinqStream;

import java.util.UUID;

public class ChatMessageRepository implements org.hiof.chatroom.persistence.ChatMessageRepository {

    private final UnitOfWork unitOfWork;

    public ChatMessageRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void add(ChatMessage message) {
        unitOfWork.getSession().save(message);
    }

    @Override
    public JinqStream<ChatMessage> query() {
        return unitOfWork.getStreamProvider().streamAll(unitOfWork.getEntityManager(), ChatMessage.class);
    }

    @Override
    public ChatMessage get(UUID id) {
        return unitOfWork.getSession().get(ChatMessage.class, id);
    }
}
