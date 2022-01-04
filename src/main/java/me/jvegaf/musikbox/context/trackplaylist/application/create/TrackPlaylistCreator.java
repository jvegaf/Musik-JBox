package me.jvegaf.musikbox.context.trackplaylist.application.create;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylist;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistPosition;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistRepository;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;

@Service
public final class TrackPlaylistCreator {

    private final TrackPlaylistRepository repository;
    private final EventBus                eventBus;

    public TrackPlaylistCreator(TrackPlaylistRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }

    public void create(PlaylistId playlistId, TrackId trackId) {
        TrackPlaylistPosition position = new TrackPlaylistPosition(repository.freePosition(playlistId));
        TrackPlaylist tp = TrackPlaylist.create(playlistId, trackId, position);
        repository.save(tp);
        eventBus.publish(tp.pullDomainEvents());
    }
}
