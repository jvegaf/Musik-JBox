package me.jvegaf.musikbox.context.tracks.application.update;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;

@Service
public final class TrackUpdater {

    private final TrackRepository repository;
    private final EventBus        eventBus;

    public TrackUpdater(TrackRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }


    public void update(UpdateTrackCommand c) {
        Track t = repository.search(new TrackId(c.trackId())).orElseThrow();
        var updatedTrack = t.updateFromPrimitives(c.title(),
                                                  c.artist(),
                                                  c.album(),
                                                  c.genre(),
                                                  c.year(),
                                                  c.bpm(),
                                                  c.key(),
                                                  c.comments());
        repository.save(updatedTrack);
        eventBus.publish(updatedTrack.pullDomainEvents());
    }
}
