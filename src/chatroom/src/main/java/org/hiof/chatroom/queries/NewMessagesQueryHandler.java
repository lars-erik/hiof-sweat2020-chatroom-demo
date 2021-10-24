package org.hiof.chatroom.queries;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewMessagesQueryHandler implements QueryHandler<NewMessagesQuery> {
    private Repository<ChatMessage> repository;
    private UnitOfWork unitOfWork;

    public NewMessagesQueryHandler(Repository<ChatMessage> repository, UnitOfWork unitOfWork) {
        this.repository = repository;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Object execute(NewMessagesQuery query) {
        try {
            return repository.query(query).map(ChatMessage::toString).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
        finally {
            unitOfWork.close();
        }
    }
}
