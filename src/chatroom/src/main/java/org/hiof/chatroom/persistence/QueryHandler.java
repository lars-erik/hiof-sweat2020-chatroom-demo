package org.hiof.chatroom.persistence;

public interface QueryHandler {
    Object query(Query query, Repository repository);
}