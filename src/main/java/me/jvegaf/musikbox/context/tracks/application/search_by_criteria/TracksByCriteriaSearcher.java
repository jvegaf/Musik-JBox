package me.jvegaf.musikbox.context.tracks.application.search_by_criteria;

import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import me.jvegaf.musikbox.shared.domain.criteria.Filters;
import me.jvegaf.musikbox.shared.domain.criteria.Order;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class TracksByCriteriaSearcher {

    private final TrackRepository repository;


    public TracksByCriteriaSearcher(TrackRepository repository) { this.repository = repository; }

    public TracksResponse search(Filters filters, Order order, Optional<Integer> limit, Optional<Integer> offset) {

        Criteria criteria = new Criteria(filters, order, limit, offset);

        return new TracksResponse(repository.matching(criteria)
                                            .stream()
                                            .map(TrackResponse::fromAggregate)
                                            .collect(Collectors.toList()));
    }
}
