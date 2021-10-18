package org.hiof.chatroom.persistence;

import org.hiof.chatroom.queries.Query;

import java.util.stream.Stream;

public interface Repository<T> {
    void add(T message);
    Stream<T> all();
    Stream<T> query(Query query) throws Exception;
    T get(String id);
}
