package me.jvegaf.musikbox.context.tracks.infrastructure.persistence;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public final class InMemoryTrackRepository implements TrackRepository {

    private final List<Track> tracks = new ArrayList<>();

    @Override
    public void save(Track track) { tracks.add(track); }

    @Override
    public void update(Track track) {
        tracks.removeIf(t -> t.id().equals(track.id()));
        tracks.add(track);
    }

    @Override
    public List<Track> searchAll() {
        return tracks;
    }

    @Override
    public Optional<Track> find(TrackId id) {
        return tracks.stream().filter(track -> track.id().equals(id)).findFirst();
    }

    @Override
    public List<Track> matching(Criteria criteria) {
        return null;
    }
}
