package com.github.jvegaf.musikbox.context.trackplaylist.application.create;

import com.github.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylist;
import com.github.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.event.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public final class TrackPlaylistCreator {

    private final TrackPlaylistRepository repository;
    private final EventBus                eventBus;

    public TrackPlaylistCreator(TrackPlaylistRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }

    public void create(String playlistId, String trackId) {
        Integer
                      position =
                repository.search(playlistId)
                          .size();
        TrackPlaylist tp       = TrackPlaylist.create(playlistId, trackId, position);
        repository.save(tp);
        eventBus.publish(tp.pullDomainEvents());
        log.info("Track: " + trackId + " added to: " + playlistId);
    }
}
