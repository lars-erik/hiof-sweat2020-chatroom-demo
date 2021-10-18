package org.hiof.chatroom.commands;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.core.TimeFactory;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.*;

import java.util.UUID;

public class SendMessageCommand {
    public String user;
    public String message;

    public void execute() throws Exception {
        UnitOfWork uow = PersistenceFactory.Instance.createUnitOfWork();
        Repository<ChatMessage> repo = PersistenceFactory.Instance.createChatMessageRepository(uow);

        ChatMessage msg = new ChatMessage();
        msg.setId(UUID.randomUUID().toString());
        msg.setUser(user);
        msg.setMessage(message);
        msg.setTime(TimeFactory.nowFactory.call());

        repo.add(msg);
        uow.saveChanges();
        uow.close();

        NotificationServiceFactory.Instance.getService().notifyNewMessage(msg);
    }
}
