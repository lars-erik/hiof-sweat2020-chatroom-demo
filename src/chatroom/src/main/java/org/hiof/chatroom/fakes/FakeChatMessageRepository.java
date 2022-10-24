package org.hiof.chatroom.fakes;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.jinq.orm.stream.JinqStream;
import org.jinq.orm.stream.NonQueryJinqStream;

import java.util.*;
import java.util.stream.Stream;

public class FakeChatMessageRepository implements ChatMessageRepository {
    List<ChatMessage> messages = new ArrayList<>();

    @Override
    public void add(ChatMessage message) {
        messages.add(message);
    }

    @Override
    public JinqStream<ChatMessage> query() {
        return new NonQueryJinqStream(messages.stream());
    }

    @Override
    public ChatMessage get(UUID id) {
        return messages.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
}
