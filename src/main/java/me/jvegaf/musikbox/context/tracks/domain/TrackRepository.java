package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.criteria.Criteria;

import java.util.List;
import java.util.Optional;

public interface TrackRepository {

    void save(Track track);

    void update(Track track);

    List<Track> searchAll();

    Optional<Track> find(TrackId id);

    List<Track> matching(Criteria criteria);
}
