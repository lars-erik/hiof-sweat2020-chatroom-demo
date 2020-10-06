package org.hiof.chatroom.core;

import org.junit.jupiter.api.*;

public class When_sending_messages {
    @Test
    public void message_are_stored_in_database() {
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke Skywalker";
        cmd.message = "This is Red Leader. We're approaching the Ison Corridor!";
        cmd.execute();

        Assertions.fail("What do we assert?");
    }
}
