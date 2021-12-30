package me.jvegaf.musikbox.context.tracks.application.upgrade;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;

public final class TrackUpgrader {

    private final TrackRepository repository;
    private final EventBus        eventBus;

    public TrackUpgrader(TrackRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }

    public void upgrade(Track track) {
        // Throw exception if track not exists ???
        if (checkExists(track)) {
            repository.save(track);
            eventBus.publish(track.pullDomainEvents());
        }
    }

    private boolean checkExists(Track track) {
        return repository.find(track.id()).isPresent();
    }


}
