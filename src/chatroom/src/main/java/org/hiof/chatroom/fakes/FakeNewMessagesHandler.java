package org.hiof.chatroom.fakes;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.Query;
import org.hiof.chatroom.persistence.QueryHandler;
import org.hiof.chatroom.persistence.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FakeNewMessagesHandler implements QueryHandler {
    @Override
    public Object query(Query query, Repository repository) {
        FakeChatMessageRepository repo = (FakeChatMessageRepository)repository;

        long count = repo.messages.stream().count();
        Stream<ChatMessage> sorted = repo.messages.stream()
                .sorted((x, y) -> Long.compare(y.getTime().getEpochSecond(), x.getTime().getEpochSecond()))
                .limit(20);
        return sorted;
    }
}
