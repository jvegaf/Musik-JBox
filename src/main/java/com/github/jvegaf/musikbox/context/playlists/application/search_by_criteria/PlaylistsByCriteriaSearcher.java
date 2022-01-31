package com.github.jvegaf.musikbox.context.playlists.application.search_by_criteria;

import com.github.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import com.github.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.criteria.Criteria;
import com.github.jvegaf.musikbox.shared.domain.criteria.Filters;
import com.github.jvegaf.musikbox.shared.domain.criteria.Order;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class PlaylistsByCriteriaSearcher {

    private final PlaylistRepository repository;


    public PlaylistsByCriteriaSearcher(PlaylistRepository repository) {this.repository = repository;}

    public PlaylistsResponse search(Filters filters, Order order, Optional<Integer> limit, Optional<Integer> offset) {

        Criteria criteria = new Criteria(filters, order, limit, offset);

        return new PlaylistsResponse(repository.matching(criteria)
                                               .stream()
                                               .map(PlaylistResponse::fromAggregate)
                                               .collect(Collectors.toList()));
    }
}
