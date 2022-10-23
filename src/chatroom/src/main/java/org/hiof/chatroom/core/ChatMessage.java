package org.hiof.chatroom.core;

import java.time.Instant;
import java.util.UUID;

public class ChatMessage {
    private UUID id;
    private Instant time;
    private String user;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(UUID id, Instant time, String user, String message) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "time='" + time.toString() + '\'' +
                ", user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
