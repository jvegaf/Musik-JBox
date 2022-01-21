package me.jvegaf.musikbox.context.tracks.application.create;

import me.jvegaf.musikbox.context.tracks.domain.*;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;

@Service
public final class TrackCreator {

    private final TrackRepository repository;
    private final EventBus        eventBus;

    public TrackCreator(TrackRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }

    public void create(
            TrackTitle title,
            TrackLocation location,
            TrackDuration duration,
            TrackArtist artist,
            TrackAlbum album,
            TrackGenre genre,
            TrackYear year,
            TrackBpm bpm,
            TrackInitKey initKey,
            TrackComments comments
    ) {
        Track track = Track.create(TrackId.create(),
                                   title,
                                   location,
                                   duration,
                                   artist,
                                   album,
                                   genre,
                                   year,
                                   bpm,
                                   initKey,
                                   comments);

        repository.save(track);
        eventBus.publish(track.pullDomainEvents());
    }
}
