package org.hiof.chatroom.queries;

import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.UnitOfWork;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LastMessagesQuery {
    public LastMessagesQuery() {
    }

    public List<String> execute() throws Exception {
        UnitOfWork unitOfWork = PersistenceFactory.instance.createUnitOfWork();
        ChatMessageRepository chatMessageRepository = PersistenceFactory.instance.createChatMessageRepository(
                unitOfWork
        );
        List<String> lastMessages = chatMessageRepository
                .query()
                .sortedDescendingBy(x -> x.getTime())
                .limit(20)
                .map(msg -> msg.getUser() + ": " + msg.getMessage())
                .collect(Collectors.toList());
        unitOfWork.close();
        return lastMessages;
    }

}