package org.hiof.chatroom.fakes;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.Query;
import org.hiof.chatroom.persistence.QueryHandler;
import org.hiof.chatroom.persistence.QueryHandlerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FakeChatMessageRepository implements ChatMessageRepository {
    Set<ChatMessage> messages = new HashSet<>();

    @Override
    public void add(ChatMessage message) {
        messages.add(message);
    }

    @Override
    public <T> T query(Query query) throws Exception {
        QueryHandler handler = QueryHandlerFactory.createFor(query);
        T result = (T)handler.query(query, this);
        return result;
    }

    @Override
    public ChatMessage get(String id) {
        return messages.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
}
