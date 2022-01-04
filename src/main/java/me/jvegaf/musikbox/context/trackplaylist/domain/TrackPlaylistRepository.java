package me.jvegaf.musikbox.context.trackplaylist.domain;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;

import java.util.List;
import java.util.Optional;

public interface TrackPlaylistRepository {

    void save(TrackPlaylist trackPlaylist);

    List<TrackPlaylist> search(PlaylistId playlistId);

    Optional<TrackPlaylist> find(TrackPlaylistId id);

    void delete(TrackPlaylist trackPlaylist);

    int freePosition(PlaylistId playlistId);

    void deleteAll(PlaylistId playlistId);
}
