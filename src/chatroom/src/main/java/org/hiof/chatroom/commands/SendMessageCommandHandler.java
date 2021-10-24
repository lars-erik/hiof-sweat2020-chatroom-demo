package org.hiof.chatroom.commands;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.core.TimeFactory;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.UnitOfWork;

import java.util.UUID;

public class SendMessageCommandHandler implements CommandHandler {
    private Repository<ChatMessage> repository;
    private UnitOfWork unitOfWork;
    private NotificationService notificationService;

    public SendMessageCommandHandler(Repository<ChatMessage> repository, UnitOfWork unitOfWork, NotificationService notificationService) {
        this.repository = repository;
        this.unitOfWork = unitOfWork;
        this.notificationService = notificationService;
    }

    @Override
    public void execute(Command command) throws Exception {
        execute((SendMessageCommand) command);
    }

    private void execute(SendMessageCommand command) throws Exception {
        try {
            ChatMessage msg = new ChatMessage();
            msg.setId(UUID.randomUUID().toString());
            msg.setUser(command.user);
            msg.setMessage(command.message);
            msg.setTime(TimeFactory.nowFactory.call());

            repository.add(msg);
            unitOfWork.saveChanges();

            notificationService.notifyNewMessage(msg);
        }
        finally {
            unitOfWork.close();
        }
    }
}
