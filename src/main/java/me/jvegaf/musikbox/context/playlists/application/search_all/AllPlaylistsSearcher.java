package me.jvegaf.musikbox.context.playlists.application.search_all;

import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public final class AllPlaylistsSearcher {

    private final PlaylistRepository repository;


    public AllPlaylistsSearcher(PlaylistRepository repository) { this.repository = repository; }

    public PlaylistsResponse search() {
        return new PlaylistsResponse(repository.searchAll().stream().map(PlaylistResponse::fromAggregate).collect(Collectors.toList()));
    }
}
