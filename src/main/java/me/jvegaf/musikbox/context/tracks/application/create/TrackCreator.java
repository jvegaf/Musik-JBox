package me.jvegaf.musikbox.context.tracks.application.create;

import me.jvegaf.musikbox.context.tracks.domain.*;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;

public final class TrackCreator {

    private final TrackRepository repository;
    private final EventBus        eventBus;

    public TrackCreator(TrackRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }

    public void create(TrackId id, TrackTitle title, TrackLocation location, TrackDuration duration) {
        Track track = Track.create(id, title, location, duration);

        repository.save(track);
        eventBus.publish(track.pullDomainEvents());
    }
}
}
