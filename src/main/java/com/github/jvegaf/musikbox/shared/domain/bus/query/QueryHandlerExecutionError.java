package com.github.jvegaf.musikbox.shared.domain.bus.query;

public final class QueryHandlerExecutionError extends RuntimeException {

    public QueryHandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
