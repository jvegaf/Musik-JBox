package me.jvegaf.musikbox.context.tracks.application.find;

import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackNotExist;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import org.springframework.stereotype.Service;

@Service
public final class TrackFinder {
    private final TrackRepository repository;

    public TrackFinder(TrackRepository repository) {
        this.repository = repository;
    }

    public TrackResponse find(TrackId id) throws TrackNotExist {
        return repository.find(id)
                         .map(TrackResponse::fromAggregate)
                         .orElseThrow(() -> new TrackNotExist(id));
    }
}
