package com.github.jvegaf.musikbox.shared.domain.track;

import com.github.jvegaf.musikbox.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class TrackCreatedDomainEvent extends DomainEvent {

    private final String name;
    private final String location;

    public TrackCreatedDomainEvent() {
        super(null);

        this.name     = null;
        this.location = null;
    }

    public TrackCreatedDomainEvent(String aggregateId, String name, String location) {
        super(aggregateId);

        this.name     = name;
        this.location = location;
    }

    public TrackCreatedDomainEvent(
            String aggregateId, String eventId, String occurredOn, String name, String location
    ) {
        super(aggregateId, eventId, occurredOn);

        this.name     = name;
        this.location = location;
    }

    @Override
    public String eventName() {
        return "track.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {{
            put("name", name);
            put("location", location);
        }};
    }

    @Override
    public TrackCreatedDomainEvent fromPrimitives(
            String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn
    ) {
        return new TrackCreatedDomainEvent(aggregateId,
                                           eventId,
                                           occurredOn,
                                           (String) body.get("name"),
                                           (String) body.get("location"));
    }

    public String name() {
        return name;
    }

    public String location() {
        return location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (o==null || getClass()!=o.getClass()) {
            return false;
        }
        TrackCreatedDomainEvent that = (TrackCreatedDomainEvent) o;
        return name.equals(that.name) && location.equals(that.location);
    }
}
