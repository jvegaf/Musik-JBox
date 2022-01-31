package com.github.jvegaf.musikbox.context.tracks.application.search_by_criteria;

import com.github.jvegaf.musikbox.context.tracks.application.TrackLibraryResponse;
import com.github.jvegaf.musikbox.context.tracks.application.TracksResponse;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.criteria.Criteria;
import com.github.jvegaf.musikbox.shared.domain.criteria.Filters;
import com.github.jvegaf.musikbox.shared.domain.criteria.Order;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class TracksByCriteriaSearcher {

    private final TrackRepository repository;


    public TracksByCriteriaSearcher(TrackRepository repository) {this.repository = repository;}

    public TracksResponse search(Filters filters, Order order, Optional<Integer> limit, Optional<Integer> offset) {

        Criteria criteria = new Criteria(filters, order, limit, offset);

        return new TracksResponse(repository.matching(criteria)
                                            .stream()
                                            .map(TrackLibraryResponse::fromAggregate)
                                            .collect(Collectors.toList()));
    }
}
