package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.criteria.Criteria;

import java.util.List;

public interface TrackRepository {

    void save(Track track);

    List<Track> searchAll();

    List<Track> matching(Criteria criteria);
}
