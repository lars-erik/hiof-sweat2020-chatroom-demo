package org.hiof.chatroom.database;

import org.hibernate.Session;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.Query;
import org.hiof.chatroom.persistence.QueryHandler;
import org.hiof.chatroom.persistence.QueryHandlerFactory;

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
    public <T> T query(Query query) throws Exception {
        QueryHandler handler = QueryHandlerFactory.createFor(query);
        T result = (T)handler.query(query, this);
        return result;
    }

    @Override
    public ChatMessage get(String id) {
        return getSession().get(ChatMessage.class, id);
    }

    public Session getSession() {
        return unitOfWork.getSession();
    }
}
