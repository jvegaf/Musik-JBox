package com.github.jvegaf.musikbox.context.playlists.application.update;

import com.github.jvegaf.musikbox.context.playlists.domain.Playlist;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.event.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public final class PlaylistUpdater {

    private final PlaylistRepository repository;
    private final EventBus           bus;

    public PlaylistUpdater(PlaylistRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus        = bus;
    }

    public void update(PlaylistId id, PlaylistName name) {
        Playlist
                 p       =
                repository.search(id)
                          .orElseThrow();
        Playlist updated = p.update(name);
        repository.save(updated);
        bus.publish(updated.pullDomainEvents());
        log.info("playlist " + p.name() + " updated to: " + updated.name());
    }
}
