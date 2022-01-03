package me.jvegaf.musikbox.context.playlists.application.update;

import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.playlists.domain.Playlist;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import me.jvegaf.musikbox.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;

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
        Playlist p = repository.find(id).orElseThrow();
        Playlist updated = p.update(name);
        repository.save(p);
        bus.publish(updated.pullDomainEvents());
        log.info("playlist " + p.name() + " updated to: " + updated.name());
    }
}
