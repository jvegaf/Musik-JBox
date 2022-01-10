package me.jvegaf.musikbox.context.tracks.application.search_all;

import me.jvegaf.musikbox.context.tracks.application.TrackLibraryResponse;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import me.jvegaf.musikbox.shared.domain.criteria.Filters;
import me.jvegaf.musikbox.shared.domain.criteria.Order;

import java.util.stream.Collectors;

@Service
public final class AllTracksSearcher {

    private final TrackRepository repository;


    public AllTracksSearcher(TrackRepository repository) {this.repository = repository;}

    public TracksResponse search() {
        return new TracksResponse(repository.matching(new Criteria(Filters.none(), Order.asc("title")))
                                            .stream()
                                            .map(TrackLibraryResponse::fromAggregate)
                                            .collect(Collectors.toList()));
    }
}
