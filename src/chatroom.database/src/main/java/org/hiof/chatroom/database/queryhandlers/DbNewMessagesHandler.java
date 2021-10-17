package org.hiof.chatroom.database.queryhandlers;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.persistence.Query;
import org.hiof.chatroom.persistence.QueryHandler;
import org.hiof.chatroom.persistence.Repository;

import java.util.stream.Collectors;

public class DbNewMessagesHandler implements QueryHandler {
    @Override
    public Object query(Query query, Repository repository) {
        ChatMessageRepository repo = (ChatMessageRepository)repository;
        org.hibernate.query.Query<ChatMessage> dbQuery = repo.getSession()
                .createQuery("SELECT msg FROM ChatMessage msg ORDER BY msg.time DESC", ChatMessage.class)
                .setMaxResults(20);
        return dbQuery.stream().map(x -> x.toString()).collect(Collectors.toList());
    }
}
