package org.hiof.chatroom.queries;

import org.hiof.chatroom.persistence.Query;

public class NewMessagesQuery extends Query {
    public int limit;

    public NewMessagesQuery(int limit) {
        this.limit = limit;
    }
}
