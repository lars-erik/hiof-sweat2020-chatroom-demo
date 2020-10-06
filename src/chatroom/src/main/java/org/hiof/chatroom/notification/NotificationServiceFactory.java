package org.hiof.chatroom.notification;

public abstract class NotificationServiceFactory {
    public static NotificationServiceFactory Instance;

    public abstract NotificationService getService();
}
