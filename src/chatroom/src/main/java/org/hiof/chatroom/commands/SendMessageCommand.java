package org.hiof.chatroom.commands;

import org.hiof.chatroom.core.*;
import org.hiof.chatroom.notification.*;
import org.hiof.chatroom.persistence.*;

import java.util.UUID;

public class SendMessageCommand {
    public String user;
    public String message;

    public void execute() throws Exception {
        UnitOfWork uow = PersistenceFactory.instance.createUnitOfWork();
        ChatMessageRepository repo = PersistenceFactory.instance.createChatMessageRepository(uow);

        ChatMessage msg = new ChatMessage();
        msg.setId(UUID.randomUUID());
        msg.setUser(user);
        msg.setMessage(message);
        msg.setTime(TimeFactory.nowFactory.call());

        repo.add(msg);
        uow.saveChanges();
        uow.close();

        NotificationServiceFactory.instance.getService().notifyNewMessage(msg);
    }
}
