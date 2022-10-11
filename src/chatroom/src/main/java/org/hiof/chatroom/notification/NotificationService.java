package org.hiof.chatroom.notification;

import org.hiof.chatroom.core.ChatMessage;

public interface NotificationService {
    void notifyNewMessage(ChatMessage message);
}
