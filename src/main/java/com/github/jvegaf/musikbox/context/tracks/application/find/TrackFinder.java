package com.github.jvegaf.musikbox.context.tracks.application.find;

import com.github.jvegaf.musikbox.context.tracks.application.TrackLibraryResponse;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackId;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackNotExist;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.TrackResponse;

@Service
public final class TrackFinder {
    private final TrackRepository repository;

    public TrackFinder(TrackRepository repository) {
        this.repository = repository;
    }

    public TrackResponse find(TrackId id) throws TrackNotExist {
        return repository.search(id)
                         .map(TrackLibraryResponse::fromAggregate)
                         .orElseThrow(() -> new TrackNotExist(id));
    }
}
