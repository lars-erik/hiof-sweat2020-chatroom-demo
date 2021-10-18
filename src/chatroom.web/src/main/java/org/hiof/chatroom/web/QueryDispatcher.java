package org.hiof.chatroom.web;

import org.hiof.chatroom.queries.Query;
import org.hiof.chatroom.queries.QueryHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class QueryDispatcher {
    static Map<Class<?>, Class<?>> handlers = new HashMap<>();

    private Function<Class, QueryHandler> createInstance;

    public QueryDispatcher(Function<Class, QueryHandler> createInstance) {
        this.createInstance = createInstance;
    }

    public static void register(Class<?> queryClass, Class<?> handlerClass) {
        handlers.put(queryClass, handlerClass);
    }

    public <T> T query(Query query) {
        Class<?> handlerType = handlers.get(query.getClass());
        QueryHandler handler = createInstance.apply(handlerType);
        return (T)handler.execute(query);
    }

}
