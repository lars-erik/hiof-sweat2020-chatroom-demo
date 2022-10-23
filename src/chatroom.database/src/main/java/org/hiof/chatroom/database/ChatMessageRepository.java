package org.hiof.chatroom.database;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hiof.chatroom.core.ChatMessage;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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
    public Stream<ChatMessage> query() {
        Session session = unitOfWork.getSession();
        Query query = session.createQuery("SELECT msg FROM ChatMessage msg");
        List<ChatMessage> msgs = query.list();
        return msgs.stream();
    }

    @Override
    public ChatMessage get(String id) {
        return unitOfWork.getSession().get(ChatMessage.class, id);
    }
}
