package com.github.jvegaf.musikbox.shared.infrastructure.bus.query;

import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.query.Query;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryNotRegisteredError;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;

@Log4j2
@Service
public final class QueryHandlersInformation {
    HashMap<Class<? extends Query>, Class<? extends QueryHandler>> indexedQueryHandlers;

    public QueryHandlersInformation() {
        Reflections                        reflections = new Reflections("com.github.jvegaf");
        Set<Class<? extends QueryHandler>> classes     = reflections.getSubTypesOf(QueryHandler.class);

        indexedQueryHandlers = formatHandlers(classes);

        classes.forEach(handler -> log.info("Registered query handler: {}", handler.getName()));
    }

    private HashMap<Class<? extends Query>, Class<? extends QueryHandler>> formatHandlers(
            Set<Class<? extends QueryHandler>> queryHandlers
    ) {
        HashMap<Class<? extends Query>, Class<? extends QueryHandler>> handlers = new HashMap<>();

        for (Class<? extends QueryHandler> handler : queryHandlers) {
            ParameterizedType      paramType  = (ParameterizedType) handler.getGenericInterfaces()[0];
            Class<? extends Query> queryClass = (Class<? extends Query>) paramType.getActualTypeArguments()[0];

            handlers.put(queryClass, handler);
        }

        return handlers;
    }

    public Class<? extends QueryHandler> search(Class<? extends Query> queryClass) throws QueryNotRegisteredError {
        Class<? extends QueryHandler> queryHandlerClass = indexedQueryHandlers.get(queryClass);

        if (null==queryHandlerClass) {
            throw new QueryNotRegisteredError(queryClass);
        }

        return queryHandlerClass;
    }
}
