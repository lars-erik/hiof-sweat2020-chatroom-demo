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
        long count = chatMessageRepository.query().count();
        List<String> lastMessages = chatMessageRepository
                .query()
                .skip(Math.max(count - 20, 0))
                .map(msg -> msg.getUser() + ": " + msg.getMessage())
                .collect(Collectors.toList());
        Collections.reverse(lastMessages);
        unitOfWork.close();
        return lastMessages;
    }

}