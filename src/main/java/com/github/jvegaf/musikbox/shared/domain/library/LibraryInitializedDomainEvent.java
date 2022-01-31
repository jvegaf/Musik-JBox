package com.github.jvegaf.musikbox.shared.domain.library;

import com.github.jvegaf.musikbox.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class LibraryInitializedDomainEvent extends DomainEvent {

    public LibraryInitializedDomainEvent() {
        super(null);
    }

    public LibraryInitializedDomainEvent(String aggregateId) {
        super(aggregateId);

    }

    public LibraryInitializedDomainEvent(String aggregateId, String eventId, String occurredOn) {
        super(aggregateId, eventId, occurredOn);
    }


    @Override
    public String eventName() {
        return "library.initizialited";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>();
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn
    ) {
        return new LibraryInitializedDomainEvent(aggregateId, eventId, occurredOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        return o!=null && getClass()==o.getClass();
    }
}
