package org.hiof.chatroom.persistence;

import org.hiof.chatroom.queries.Query;

import java.util.stream.Stream;

public interface RepositoryQueryHandler<T> {
    Stream<T> query(Query query, Repository repository);
}