package org.hiof.chatroom.web;

import org.hiof.chatroom.commands.CommandHandler;
import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.commands.SendMessageCommandHandler;
import org.hiof.chatroom.database.DatabaseManager;
import org.hiof.chatroom.database.queryhandlers.NewMessagesDbQueryHandler;
import org.hiof.chatroom.notification.NotificationService;
import org.hiof.chatroom.persistence.RepositoryQueryHandler;
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
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        RepositoryQueryHandlerFactory.register(NewMessagesQuery.class, NewMessagesDbQueryHandler.class);
        QueryDispatcher.register(NewMessagesQuery.class, NewMessagesQueryHandler.class);
        CommandDispatcher.register(SendMessageCommand.class, SendMessageCommandHandler.class);

        final ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) WebApplication::configure)
                .sources(WebApplication.class)
                .run(args);

        Class.forName("org.sqlite.JDBC");
        DatabaseManager.ensureDatabase("./db/chat.db", false);

    }

    private static void configure(GenericApplicationContext ctx) {
        ctx.registerBean(NotificationService.class, () -> message -> {
            NotificationDispatcher dispatcher = ctx.getBean(NotificationDispatcher.class);
            dispatcher.dispatch(message);
        });

        ctx.registerBean(org.hiof.chatroom.database.UnitOfWork.class, bd -> bd.setScope("request"));
        ctx.registerBean(org.hiof.chatroom.database.ChatMessageRepository.class, WebApplication::asTransient);

        ctx.registerBean(NewMessagesQueryHandler.class, WebApplication::asTransient);
        ctx.registerBean(SendMessageCommandHandler.class, WebApplication::asTransient);

        ctx.registerBean(QueryDispatcher.class, () ->
            new QueryDispatcher(
                t -> (QueryHandler)ctx.getBean(t)
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