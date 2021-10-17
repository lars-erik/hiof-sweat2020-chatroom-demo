package org.hiof.chatroom.fakes;

import org.hiof.chatroom.persistence.Query;
import org.hiof.chatroom.persistence.QueryHandler;
import org.hiof.chatroom.persistence.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class FakeNewMessagesHandler implements QueryHandler {
    @Override
    public Object query(Query query, Repository repository) {
        FakeChatMessageRepository repo = (FakeChatMessageRepository)repository;

        long count = repo.messages.stream().count();
        List<String> sorted = repo.messages.stream()
                .sorted((x, y) -> Long.compare(y.getTime().getEpochSecond(), x.getTime().getEpochSecond()))
                .limit(20)
                .map(msg -> msg.toString())
                .collect(Collectors.toList());
        return sorted;
    }
}
