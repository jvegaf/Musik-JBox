package com.github.jvegaf.musikbox.context.playlists.application.search_all;

import com.github.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import com.github.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.criteria.Criteria;
import com.github.jvegaf.musikbox.shared.domain.criteria.Filters;
import com.github.jvegaf.musikbox.shared.domain.criteria.Order;

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
