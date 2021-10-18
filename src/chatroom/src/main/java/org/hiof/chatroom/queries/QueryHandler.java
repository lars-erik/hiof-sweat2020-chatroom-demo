package org.hiof.chatroom.queries;

public interface QueryHandler<TQuery> {
    Object execute(TQuery query);
}
