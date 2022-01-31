package com.github.jvegaf.musikbox.context.playlists.application.find;

import com.github.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistNotExist;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;

@Service
public final class PlaylistFinder {

    private final PlaylistRepository repository;

    public PlaylistFinder(PlaylistRepository repository) {
        this.repository = repository;
    }

    public PlaylistResponse find(PlaylistId id) throws PlaylistNotExist {
        return repository.search(id)
                         .map(PlaylistResponse::fromAggregate)
                         .orElseThrow(() -> new PlaylistNotExist(id));
    }
}
