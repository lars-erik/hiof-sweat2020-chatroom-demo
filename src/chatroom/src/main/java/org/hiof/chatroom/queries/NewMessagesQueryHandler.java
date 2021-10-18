package org.hiof.chatroom.queries;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewMessagesQueryHandler implements QueryHandler<NewMessagesQuery> {
    @Override
    public Object execute(NewMessagesQuery query) {
        try {
            UnitOfWork unitOfWork = PersistenceFactory.Instance.createUnitOfWork();
            Repository<ChatMessage> chatMessageRepository = PersistenceFactory.Instance.createChatMessageRepository(unitOfWork);
            List<String> lastMessages = chatMessageRepository.query(query).map(x -> x.toString()).collect(Collectors.toList());
            unitOfWork.close();
            return lastMessages;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
