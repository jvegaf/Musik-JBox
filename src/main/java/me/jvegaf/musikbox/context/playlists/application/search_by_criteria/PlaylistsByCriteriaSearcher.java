package me.jvegaf.musikbox.context.playlists.application.search_by_criteria;

import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import me.jvegaf.musikbox.shared.domain.criteria.Filters;
import me.jvegaf.musikbox.shared.domain.criteria.Order;

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
