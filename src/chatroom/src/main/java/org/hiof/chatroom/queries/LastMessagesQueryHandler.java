package org.hiof.chatroom.queries;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LastMessagesQueryHandler implements QueryHandler<LastMessagesQuery> {
    private Repository<ChatMessage> repository;

    public LastMessagesQueryHandler(Repository<ChatMessage> repository) {
        this.repository = repository;
    }

    @Override
    public Object execute(LastMessagesQuery query) {
        try {
            return repository
                    .query()
                    .sortedDescendingBy(x -> x.getTime())
                    .limit(20)
                    .map(msg -> msg.format())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
