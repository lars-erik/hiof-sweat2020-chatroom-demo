package org.hiof.chatroom.core;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

public class ChatMessage {
    private String id;
    private Instant time;
    private String user;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String id, Instant time, String user, String message) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.message = message;
    }

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

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("HH:mm:ss")
            .withZone(ZoneId.of("UTC"));

    @Override
    public String toString() {
        String timeString = formatter.toFormat().format(getTime());
        return String.format("%1$s <%2$s>: %3$s", timeString, getUser(), getMessage());
    }
}
