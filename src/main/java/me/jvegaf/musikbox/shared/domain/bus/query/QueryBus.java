package me.jvegaf.musikbox.shared.domain.bus.query;

public interface QueryBus {

    <R> Response ask(Query query) throws QueryHandlerExecutionError;
}
