package me.jvegaf.musikbox.context.playlists.domain;

import me.jvegaf.musikbox.shared.domain.criteria.Criteria;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository {

    void save(Playlist track);


    List<Playlist> searchAll();

    Optional<Playlist> find(PlaylistId id);

    List<Playlist> matching(Criteria criteria);
}
