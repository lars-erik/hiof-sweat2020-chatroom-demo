package org.hiof.chatroom.persistence;

import org.jinq.orm.stream.JinqStream;

import java.util.UUID;

public interface Repository<T> {
    void add(T message);
    JinqStream<T> query();
    T get(UUID id);
}
