package me.jvegaf.musikbox.context.playlists.application.create;

import me.jvegaf.musikbox.context.playlists.domain.Playlist;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;

@Service
public final class PlaylistCreator {

    private final PlaylistRepository repository;
    private final EventBus           eventBus;

    public PlaylistCreator(PlaylistRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus   = eventBus;
    }

    public void create(PlaylistName name) {
        Playlist playlist = Playlist.create(name);

        repository.save(playlist);
        eventBus.publish(playlist.pullDomainEvents());
    }
}
