package org.hiof.chatroom.persistence;

public interface UnitOfWork {
    void saveChanges();
    void close();
}
