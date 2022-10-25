package org.hiof.chatroom.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

public class When_formatting_messages {
    @Test
    public void outputs_time_user_and_message() {
        ChatMessage msg = new ChatMessage(
                UUID.fromString("0a694974-7bfa-4a5a-b49e-845e96916ba9"),
                Instant.parse("2020-01-01T10:00:05Z"),
                "Luke",
                "But I was going into Tosche Station to pick up some power converters!");
        Assertions.assertEquals(
                "10:00:05 <Luke>: But I was going into Tosche Station to pick up some power converters!",
                msg.format()
        );
    }
}
