package com.github.jvegaf.musikbox.shared.domain.track_playlist;

import com.github.jvegaf.musikbox.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class TrackPlaylistCreatedDomainEvent extends DomainEvent {


    public TrackPlaylistCreatedDomainEvent() {
        super(null);

    }

    public TrackPlaylistCreatedDomainEvent(String aggregateId) {
        super(aggregateId);

    }

    public TrackPlaylistCreatedDomainEvent(String aggregateId, String eventId, String occurredOn) {
        super(aggregateId, eventId, occurredOn);

    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), eventId(), occurredOn());
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (o==null || getClass()!=o.getClass()) {
            return false;
        }
        TrackPlaylistCreatedDomainEvent that = (TrackPlaylistCreatedDomainEvent) o;
        return eventName().equals(that.eventId());
    }

    @Override
    public String eventName() {
        return "track.added.to.playlist";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>() {{
            put("aggregateId", aggregateId());
            put("eventId", eventId());
            put("ocurredOn", occurredOn());
        }};
    }

    @Override
    public TrackPlaylistCreatedDomainEvent fromPrimitives(
            String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn
    ) {
        return new TrackPlaylistCreatedDomainEvent(aggregateId, eventId, occurredOn);
    }
}
