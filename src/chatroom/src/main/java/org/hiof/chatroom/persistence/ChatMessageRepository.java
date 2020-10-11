package org.hiof.chatroom.persistence;

import org.hiof.chatroom.core.ChatMessage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ChatMessageRepository {
    void add(ChatMessage message);
    Stream<ChatMessage> query();
    ChatMessage get(String id);
    List<String> getLastMessages();
}
