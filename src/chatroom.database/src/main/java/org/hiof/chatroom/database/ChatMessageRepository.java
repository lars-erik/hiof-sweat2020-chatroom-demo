package org.hiof.chatroom.database;

import org.hibernate.Session;
import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.queries.Query;
import org.hiof.chatroom.persistence.RepositoryQueryHandler;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;

import java.util.stream.Stream;

public class ChatMessageRepository implements org.hiof.chatroom.persistence.Repository<ChatMessage> {

    private final UnitOfWork unitOfWork;

    public ChatMessageRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void add(ChatMessage message) {
        getSession().save(message);
    }

    @Override
    public Stream<ChatMessage> all() {
        return getSession().createQuery("SELECT msg FROM ChatMessage msg", ChatMessage.class).stream();
    }

    @Override
    public Stream<ChatMessage> query(Query query) throws Exception {
        RepositoryQueryHandler handler = RepositoryQueryHandlerFactory.createFor(query);
        Stream<ChatMessage> result = (Stream<ChatMessage>)handler.query(query, this);
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
