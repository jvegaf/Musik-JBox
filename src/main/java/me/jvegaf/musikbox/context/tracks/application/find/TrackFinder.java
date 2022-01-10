package me.jvegaf.musikbox.context.tracks.application.find;

import me.jvegaf.musikbox.context.tracks.application.TrackLibraryResponse;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackNotExist;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.TrackResponse;

@Service
public final class TrackFinder {
    private final TrackRepository repository;

    public TrackFinder(TrackRepository repository) {
        this.repository = repository;
    }

    public TrackResponse find(TrackId id) throws TrackNotExist {
        return repository.search(id).map(TrackLibraryResponse::fromAggregate).orElseThrow(() -> new TrackNotExist(id));
    }
}
