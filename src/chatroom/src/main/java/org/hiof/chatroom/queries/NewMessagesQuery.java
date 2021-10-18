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
}
