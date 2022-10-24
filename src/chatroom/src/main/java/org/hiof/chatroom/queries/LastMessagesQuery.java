package org.hiof.chatroom.queries;

import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.PersistenceFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LastMessagesQuery {
    public LastMessagesQuery() {
    }

    public List<String> execute() throws Exception {
        ChatMessageRepository chatMessageRepository = PersistenceFactory.instance.createChatMessageRepository(
                PersistenceFactory.instance.createUnitOfWork()
        );
        long count = chatMessageRepository.query().count();
        List<String> lastMessages = chatMessageRepository
                .query()
                .skip(Math.max(count - 20, 0))
                .map(msg -> msg.getUser() + ": " + msg.getMessage())
                .collect(Collectors.toList());
        Collections.reverse(lastMessages);
        return lastMessages;
    }

}