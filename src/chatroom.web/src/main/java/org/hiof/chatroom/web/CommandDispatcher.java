package org.hiof.chatroom.web;

import org.hiof.chatroom.commands.Command;
import org.hiof.chatroom.commands.CommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandDispatcher {
    static Map<Class<?>, Class<?>> handlers = new HashMap<>();
    private Function<Class, CommandHandler> createInstance;

    public CommandDispatcher(Function<Class, CommandHandler> createInstance) {
        this.createInstance = createInstance;
    }

    public static void register(Class<?> commandClass, Class<?> handlerClass) {
        handlers.put(commandClass, handlerClass);
    }

    public void execute(Command command) throws Exception {
        Class<?> handlerType = handlers.get(command.getClass());
        CommandHandler handler = createInstance.apply(handlerType);
        handler.execute(command);
    }
}
