package org.hiof.chatroom.notification;

public abstract class NotificationServiceFactory {
    public static NotificationServiceFactory instance;

    public abstract NotificationService getService();
}
