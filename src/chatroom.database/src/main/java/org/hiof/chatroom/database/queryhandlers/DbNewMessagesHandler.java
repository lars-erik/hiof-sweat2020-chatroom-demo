package org.hiof.chatroom.database.queryhandlers;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.ChatMessageRepository;
import org.hiof.chatroom.queries.Query;
import org.hiof.chatroom.persistence.RepositoryQueryHandler;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.queries.NewMessagesQuery;

import java.util.stream.Stream;

public class DbNewMessagesHandler implements RepositoryQueryHandler<ChatMessage> {
    @Override
    public Stream<ChatMessage> query(Query query, Repository repository) {
        NewMessagesQuery typedQuery = (NewMessagesQuery)query;
        ChatMessageRepository repo = (ChatMessageRepository)repository;
        org.hibernate.query.Query<ChatMessage> dbQuery = repo.getSession()
                .createQuery("SELECT msg FROM ChatMessage msg ORDER BY msg.time DESC", ChatMessage.class)
                .setMaxResults(typedQuery.limit);
        return dbQuery.stream();
    }
}
