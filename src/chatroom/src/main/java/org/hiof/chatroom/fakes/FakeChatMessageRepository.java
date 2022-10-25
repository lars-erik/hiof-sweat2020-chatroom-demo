package org.hiof.chatroom.fakes;

import org.hiof.chatroom.core.ChatMessage;
import org.jinq.orm.stream.*;
import org.hiof.chatroom.persistence.*;

import java.util.*;
import java.util.stream.Stream;

public class FakeChatMessageRepository implements Repository<ChatMessage> {
    Set<ChatMessage> messages = new HashSet<>();

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
