package org.hiof.chatroom.persistence;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class QueryHandlerFactory {
    static Map<Class<? extends Query>, Class<? extends QueryHandler>> handlers = new HashMap<>();

    public static void register(Class<? extends Query> queryType, Class<? extends QueryHandler> handlerType)
    {
        handlers.put(queryType, handlerType);
    }

    public static QueryHandler createFor(Query query) throws Exception
    {
        Class<? extends QueryHandler> handlerType = handlers.get(query.getClass());
        if (handlerType == null) {
            throw new Exception("No registered handler, or not parameterless constructor");
        }
        try {
            QueryHandler handler = handlerType.newInstance();
            return handler;
        } catch (InstantiationException e) {
            throw new Exception("No registered handler, or not parameterless constructor");
        } catch (IllegalAccessException e) {
            throw new Exception("No registered handler, or not parameterless constructor");
        }
    }
}
