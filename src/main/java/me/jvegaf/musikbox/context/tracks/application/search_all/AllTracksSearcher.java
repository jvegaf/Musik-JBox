package me.jvegaf.musikbox.context.tracks.application.search_all;

import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;

import java.util.stream.Collectors;

public final class AllTracksSearcher {

    private final TrackRepository repository;


    public AllTracksSearcher(TrackRepository repository) { this.repository = repository; }

    public TracksResponse search() {
        return new TracksResponse(repository.searchAll()
                                            .stream()
                                            .map(TrackResponse::fromAggregate)
                                            .collect(Collectors.toList()));
    }
}
