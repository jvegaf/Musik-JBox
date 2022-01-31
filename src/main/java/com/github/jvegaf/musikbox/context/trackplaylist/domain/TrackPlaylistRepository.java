package com.github.jvegaf.musikbox.context.trackplaylist.domain;

import java.util.List;
import java.util.Optional;

public interface TrackPlaylistRepository {

    void save(TrackPlaylist trackPlaylist);

    List<TrackPlaylist> search(String playlistId);

    Optional<TrackPlaylist> find(TrackPlaylistId id);

    void delete(TrackPlaylist trackPlaylist);

    void deleteAll(String playlistId);
}
