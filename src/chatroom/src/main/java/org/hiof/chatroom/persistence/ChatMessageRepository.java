package org.hiof.chatroom.persistence;

import org.hiof.chatroom.core.ChatMessage;
import org.jinq.orm.stream.JinqStream;

import java.util.UUID;
import java.util.stream.Stream;

public interface ChatMessageRepository {
    void add(ChatMessage message);
    JinqStream<ChatMessage> query();
    ChatMessage get(UUID id);
}
