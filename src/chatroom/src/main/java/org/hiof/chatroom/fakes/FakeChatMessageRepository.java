package org.hiof.chatroom.fakes;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.queries.Query;
import org.hiof.chatroom.persistence.RepositoryQueryHandler;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.persistence.Repository;

import java.util.*;
import java.util.stream.Stream;

public class FakeChatMessageRepository implements Repository<ChatMessage> {
    Set<ChatMessage> messages = new HashSet<>();

    @Override
    public void add(ChatMessage message) {
        messages.add(message);
    }

    @Override
    public Stream<ChatMessage> all() {
        return messages.stream();
    }

    @Override
    public Stream<ChatMessage> query(Query query) throws Exception {
        RepositoryQueryHandler handler = RepositoryQueryHandlerFactory.createFor(query);
        Stream<ChatMessage> result = (Stream<ChatMessage>)handler.query(query, this);
        return result;
    }

    @Override
    public ChatMessage get(String id) {
        return messages.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
}
