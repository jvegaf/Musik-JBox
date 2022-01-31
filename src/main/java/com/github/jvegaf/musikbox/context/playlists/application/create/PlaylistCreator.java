package com.github.jvegaf.musikbox.context.playlists.application.create;

import com.github.jvegaf.musikbox.context.playlists.domain.Playlist;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.event.EventBus;

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
