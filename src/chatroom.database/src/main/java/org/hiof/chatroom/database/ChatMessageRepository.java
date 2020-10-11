package org.hiof.chatroom.database;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hiof.chatroom.core.ChatMessage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatMessageRepository implements org.hiof.chatroom.persistence.ChatMessageRepository {

    private final UnitOfWork unitOfWork;

    public ChatMessageRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void add(ChatMessage message) {
        getSession().save(message);
    }

    @Override
    public Stream<ChatMessage> query() {
        Session session = getSession();
        org.hibernate.query.Query<ChatMessage> query = session.createQuery("SELECT msg FROM ChatMessage msg", ChatMessage.class);
        return query.stream();
    }

    @Override
    public ChatMessage get(String id) {
        return getSession().get(ChatMessage.class, id);
    }

    public List<String> getLastMessages() {
        org.hibernate.query.Query<ChatMessage> query = getSession()
                .createQuery("SELECT msg FROM ChatMessage msg ORDER BY msg.time DESC", ChatMessage.class)
                .setMaxResults(20);
        return query.stream().map(x -> x.toString()).collect(Collectors.toList());
    }

    private Session getSession() {
        return unitOfWork.getSession();
    }
}
