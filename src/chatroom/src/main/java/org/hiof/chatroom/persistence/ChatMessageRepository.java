package org.hiof.chatroom.persistence;

import org.hiof.chatroom.core.ChatMessage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ChatMessageRepository extends Repository {
    void add(ChatMessage message);
    Stream<ChatMessage> query();
    <T> T query(Query query) throws Exception;
    ChatMessage get(String id);
    List<String> getLastMessages();
}
