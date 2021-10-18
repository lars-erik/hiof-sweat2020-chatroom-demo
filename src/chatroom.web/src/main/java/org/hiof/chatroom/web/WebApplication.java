package org.hiof.chatroom.web;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.queryhandlers.NewMessagesDbQueryHandler;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        final ConfigurableApplicationContext ctx = SpringApplication.run(WebApplication.class, args);

        Class.forName("org.sqlite.JDBC");
        DatabaseManager.ensureDatabase("./db/chat.db", false);

        PersistenceFactory.Instance = new org.hiof.chatroom.database.PersistenceFactory();
        RepositoryQueryHandlerFactory.register(NewMessagesQuery.class, NewMessagesDbQueryHandler.class);

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