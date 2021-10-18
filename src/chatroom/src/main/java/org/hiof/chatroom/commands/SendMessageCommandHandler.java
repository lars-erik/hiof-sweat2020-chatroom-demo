package org.hiof.chatroom.commands;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.core.TimeFactory;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;

import java.util.UUID;

public class SendMessageCommandHandler implements CommandHandler {
    @Override
    public void execute(Command command) {
        execute((SendMessageCommand) command);
    }

    private void execute(SendMessageCommand command) {
        try {
            UnitOfWork uow = PersistenceFactory.Instance.createUnitOfWork();
            Repository<ChatMessage> repo = PersistenceFactory.Instance.createChatMessageRepository(uow);

            ChatMessage msg = new ChatMessage();
            msg.setId(UUID.randomUUID().toString());
            msg.setUser(command.user);
            msg.setMessage(command.message);
            msg.setTime(TimeFactory.nowFactory.call());

            repo.add(msg);
            uow.saveChanges();
            uow.close();

            NotificationServiceFactory.Instance.getService().notifyNewMessage(msg);
        }
        catch (Exception ex) {
            System.out.println("This didn't go well.");
            // TODO: Implement logging. 👼
        }
    }
}
