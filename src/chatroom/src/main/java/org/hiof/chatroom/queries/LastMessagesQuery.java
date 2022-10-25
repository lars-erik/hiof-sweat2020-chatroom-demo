package org.hiof.chatroom.queries;

public class LastMessagesQuery extends Query {
    public int limit;

    public LastMessagesQuery(int limit) {
        this.limit = limit;
    }
}
