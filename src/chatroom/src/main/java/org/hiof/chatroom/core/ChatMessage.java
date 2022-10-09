package org.hiof.chatroom.core;

import java.util.UUID;

public class ChatMessage {
    private String id;
    private String user;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public ChatMessage() {
    }

    public ChatMessage(String id, String user, String message) {
        this.id = id;
        this.user = user;
        this.message = message;
    }
}
