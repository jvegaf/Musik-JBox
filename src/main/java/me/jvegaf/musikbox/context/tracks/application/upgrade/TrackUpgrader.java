package me.jvegaf.musikbox.context.tracks.application.upgrade;

import me.jvegaf.musikbox.context.tracks.domain.*;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;

@Service
public final class TrackUpgrader {

    private final TrackRepository repository;
    private final EventBus        eventBus;

    public TrackUpgrader(TrackRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }


    public void upgrade(TrackId id,
                        TrackTitle title,
                        TrackArtist artist,
                        TrackAlbum album,
                        TrackGenre genre,
                        TrackYear year,
                        TrackBpm bpm,
                        TrackInitKey initKey,
                        TrackComments comments
    ) {
        Track t = repository.find(id).orElseThrow();
        var updatedTrack = t.improveMetadata(
                title,
                artist,
                album,
                genre,
                year,
                bpm,
                initKey,
                comments
        );
        repository.save(updatedTrack);
        eventBus.publish(updatedTrack.pullDomainEvents());
    }
}
