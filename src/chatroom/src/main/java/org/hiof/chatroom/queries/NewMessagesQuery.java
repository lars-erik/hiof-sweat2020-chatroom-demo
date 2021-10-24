package org.hiof.chatroom.queries;

public class NewMessagesQuery extends Query {
    public int limit;

    public NewMessagesQuery(int limit) {
        this.limit = limit;
    }
}
