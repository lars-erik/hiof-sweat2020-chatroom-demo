package org.hiof.chatroom.core;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.support.PersistenceSupport;
import org.junit.jupiter.api.*;

public class When_sending_messages {
    PersistenceSupport support;

    @BeforeEach
    public void initialize_persistence() {
        support = new PersistenceSupport();
    }

    @Test
    public void message_are_stored_in_database() {
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = "Luke Skywalker";
        cmd.message = "This is Red Leader. We're approaching the Ison Corridor!";
        cmd.execute();

        ChatMessageRepository repo = support.getChatMessageRepository();
        ChatMessage last = repo.query().skip(repo.query().count() - 1).findFirst().get();

        Assertions.assertEquals(cmd.user, last.getUser());
        Assertions.assertEquals(cmd.message, last.getMessage());
    }
}
