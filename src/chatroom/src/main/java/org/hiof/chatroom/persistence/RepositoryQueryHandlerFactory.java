package org.hiof.chatroom.persistence;

import org.hiof.chatroom.queries.Query;

import java.util.HashMap;
import java.util.Map;

public class RepositoryQueryHandlerFactory {
    static Map<Class<? extends Query>, Class<? extends RepositoryQueryHandler>> handlers = new HashMap<>();

    public static void register(Class<? extends Query> queryType, Class<? extends RepositoryQueryHandler> handlerType)
    {
        handlers.put(queryType, handlerType);
    }

    public static RepositoryQueryHandler createFor(Query query) throws Exception
    {
        Class<? extends RepositoryQueryHandler> handlerType = handlers.get(query.getClass());
        if (handlerType == null) {
            throw new Exception("No registered handler, or not parameterless constructor");
        }
        try {
            RepositoryQueryHandler handler = handlerType.newInstance();
            return handler;
        } catch (InstantiationException e) {
            throw new Exception("No registered handler, or not parameterless constructor");
        } catch (IllegalAccessException e) {
            throw new Exception("No registered handler, or not parameterless constructor");
        }
    }
}
