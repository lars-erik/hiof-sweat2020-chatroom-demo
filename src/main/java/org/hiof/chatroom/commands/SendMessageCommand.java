package org.hiof.chatroom.commands;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.*;

import java.util.UUID;

public class SendMessageCommand {
    public String user;
    public String message;

    public void execute() {
        UnitOfWork uow = PersistenceFactory.instance.createUnitOfWork();
        ChatMessageRepository repo = PersistenceFactory.instance.createChatMessageRepository(uow);

        ChatMessage msg = new ChatMessage();
        msg.setId(UUID.randomUUID());
        msg.setUser(user);
        msg.setMessage(message);

        repo.add(msg);
        uow.saveChanges();

        NotificationServiceFactory.instance.getService().notifyNewMessages();
    }
}
