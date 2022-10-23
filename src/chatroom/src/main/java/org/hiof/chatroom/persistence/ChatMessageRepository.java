package org.hiof.chatroom.persistence;

import org.hiof.chatroom.core.ChatMessage;

import java.util.UUID;
import java.util.stream.Stream;

public interface ChatMessageRepository {
    void add(ChatMessage message);
    Stream<ChatMessage> query();
    ChatMessage get(String id);
}
