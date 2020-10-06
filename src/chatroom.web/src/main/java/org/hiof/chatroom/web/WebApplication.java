package org.hiof.chatroom.web;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.fakes.FakePersistenceFactory;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class WebApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(WebApplication.class, args);

        PersistenceFactory.Instance = new FakePersistenceFactory();
        NotificationServiceFactory.Instance = new NotificationServiceFactory() {
            @Override
            public NotificationService getService() {
                return new NotificationService() {
                    public void notifyNewMessage(ChatMessage message) {
                        NotificationDispatcher dispatcher = ctx.getBean(NotificationDispatcher.class);
                        dispatcher.dispatch(message);
                    }
                };
            }
        };
    }

}