package com.github.jvegaf.musikbox.shared.domain.playlist;

import com.github.jvegaf.musikbox.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class PlaylistUpdatedDomainEvent extends DomainEvent {

    private final String name;


    public PlaylistUpdatedDomainEvent() {
        super(null);
        this.name = null;
    }

    public PlaylistUpdatedDomainEvent(String aggregateId, String name) {
        super(aggregateId);

        this.name = name;
    }

    public PlaylistUpdatedDomainEvent(String aggregateId, String eventId, String occurredOn, String name) {
        super(aggregateId, eventId, occurredOn);
        this.name = name;
    }


    @Override
    public String eventName() {
        return "playlist.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>() {{
            put("name", name);
        }};
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn
    ) {
        return new PlaylistUpdatedDomainEvent(aggregateId, eventId, occurredOn, (String) body.get("name"));
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
        PlaylistUpdatedDomainEvent that = (PlaylistUpdatedDomainEvent) o;
        return name.equals(that.name);
    }
}
