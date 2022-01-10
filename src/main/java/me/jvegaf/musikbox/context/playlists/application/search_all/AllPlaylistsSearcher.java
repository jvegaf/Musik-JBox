package me.jvegaf.musikbox.context.playlists.application.search_all;

import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import me.jvegaf.musikbox.shared.domain.criteria.Filters;
import me.jvegaf.musikbox.shared.domain.criteria.Order;

import java.util.stream.Collectors;

@Service
public final class AllPlaylistsSearcher {

    private final PlaylistRepository repository;


    public AllPlaylistsSearcher(PlaylistRepository repository) {this.repository = repository;}

    public PlaylistsResponse search() {
        return new PlaylistsResponse(repository.matching(new Criteria(Filters.none(), Order.asc("name")))
                                               .stream()
                                               .map(PlaylistResponse::fromAggregate)
                                               .collect(Collectors.toList()));
    }
}
