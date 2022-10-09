package org.hiof.chatroom.web;

import org.hiof.chatroom.commands.CommandHandler;
import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.commands.SendMessageCommandHandler;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.queryhandlers.NewMessagesDbQueryHandler;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.RepositoryQueryHandlerFactory;
import org.hiof.chatroom.queries.NewMessagesQuery;
import org.hiof.chatroom.queries.NewMessagesQueryHandler;
import org.hiof.chatroom.queries.QueryHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        configureFactories();

        runSpring(args);

        Class.forName("org.sqlite.JDBC");
        DatabaseManager.ensureDatabase("./db/chat.db", false);

    }

    public static ConfigurableApplicationContext runSpring(String[] args) {
        return new SpringApplicationBuilder()
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> configure(ctx, "request"))
                .sources(WebApplication.class)
                .run(args);
    }

    public static void configureFactories() {
        RepositoryQueryHandlerFactory.register(NewMessagesQuery.class, NewMessagesDbQueryHandler.class);

        QueryDispatcher.register(NewMessagesQuery.class, NewMessagesQueryHandler.class);

        CommandDispatcher.register(SendMessageCommand.class, SendMessageCommandHandler.class);
    }

    public static void configure(GenericApplicationContext ctx, String unitOfWorkScope) {
        ctx.registerBean(NotificationService.class, () -> message -> {
            NotificationDispatcher dispatcher = ctx.getBean(NotificationDispatcher.class);
            dispatcher.dispatch(message);
        });

        configureExceptNotificationService(ctx, unitOfWorkScope);
    }

    public static void configureExceptNotificationService(GenericApplicationContext ctx, String unitOfWorkScope) {
        ctx.registerBean(org.hiof.chatroom.database.UnitOfWork.class, bd -> bd.setScope(unitOfWorkScope));
        ctx.registerBean(org.hiof.chatroom.database.ChatMessageRepository.class, WebApplication::asTransient);

        ctx.registerBean(NewMessagesQueryHandler.class, WebApplication::asTransient);
        ctx.registerBean(SendMessageCommandHandler.class, WebApplication::asTransient);

        ctx.registerBean(QueryDispatcher.class, () ->
            new QueryDispatcher(
                t -> (QueryHandler) ctx.getBean(t)
            )
        );

        ctx.registerBean(CommandDispatcher.class, () ->
            new CommandDispatcher(
                t -> (CommandHandler) ctx.getBean(t)
            )
        );
    }

    private static void asTransient(BeanDefinition beanDefinition) {
        beanDefinition.setScope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE);
    }

}