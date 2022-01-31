package com.github.jvegaf.musikbox.context.tracks.domain;

import com.github.jvegaf.musikbox.shared.domain.criteria.Criteria;

import java.util.List;
import java.util.Optional;

public interface TrackRepository {

    void save(Track track);

    Optional<Track> search(TrackId id);

    List<Track> matching(Criteria criteria);
}
