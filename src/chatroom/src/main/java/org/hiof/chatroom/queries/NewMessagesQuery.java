package org.hiof.chatroom.queries;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewMessagesQuery extends Query {
    public int limit;

    public NewMessagesQuery(int limit) {
        this.limit = limit;
    }

    public List<String> execute() {
        try {
            UnitOfWork unitOfWork = PersistenceFactory.Instance.createUnitOfWork();
            Repository<ChatMessage> chatMessageRepository = PersistenceFactory.Instance.createChatMessageRepository(unitOfWork);
            List<String> lastMessages = chatMessageRepository.query(this).map(x -> x.toString()).collect(Collectors.toList());
            unitOfWork.close();
            return lastMessages;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
