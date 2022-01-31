package com.github.jvegaf.musikbox.shared.domain.playlist;

import com.github.jvegaf.musikbox.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class PlaylistCreatedDomainEvent extends DomainEvent {

    private final String name;


    public PlaylistCreatedDomainEvent() {
        super(null);
        this.name = null;
    }

    public PlaylistCreatedDomainEvent(String aggregateId, String name) {
        super(aggregateId);

        this.name = name;
    }

    public PlaylistCreatedDomainEvent(String aggregateId, String eventId, String occurredOn, String name) {
        super(aggregateId, eventId, occurredOn);
        this.name = name;
    }


    @Override
    public String eventName() {
        return "playlist.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {{
            put("name", name);
        }};
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn
    ) {
        return new PlaylistCreatedDomainEvent(aggregateId, eventId, occurredOn, (String) body.get("name"));
    }

    public String name() {return name;}

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (o==null || getClass()!=o.getClass()) {
            return false;
        }
        PlaylistCreatedDomainEvent that = (PlaylistCreatedDomainEvent) o;
        return name.equals(that.name);
    }
}
