package org.hiof.chatroom.web;

import org.hiof.chatroom.core.ChatMessage;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.queryhandlers.NewMessagesDbQueryHandler;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.notification.NotificationServiceFactory;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.hiof.chatroom.persistence.Repository;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.persistence.UnitOfWork;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        final ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) WebApplication::configure)
                .sources(WebApplication.class)
                .run(args);

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

    private static void configure(GenericApplicationContext ctx) {
        ctx.registerBean(NotificationService.class, () -> message -> {
            NotificationDispatcher dispatcher = ctx.getBean(NotificationDispatcher.class);
            dispatcher.dispatch(message);
        });

        ctx.registerBean(org.hiof.chatroom.database.UnitOfWork.class);
        ctx.registerBean(org.hiof.chatroom.database.ChatMessageRepository.class);
    }

}