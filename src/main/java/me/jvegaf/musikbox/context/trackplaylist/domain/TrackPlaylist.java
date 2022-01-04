package me.jvegaf.musikbox.context.trackplaylist.domain;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.shared.domain.AggregateRoot;
import me.jvegaf.musikbox.shared.domain.track_playlist.TrackPlaylistCreatedDomainEvent;

import java.util.Objects;

public final class TrackPlaylist extends AggregateRoot {

    private final TrackPlaylistId       id;
    private final PlaylistId            playlistId;
    private final TrackId               trackId;
    private final TrackPlaylistPosition position;

    public TrackPlaylist() {
        this.id         = null;
        this.playlistId = null;
        this.trackId    = null;
        this.position   = null;
    }

    public TrackPlaylist(TrackPlaylistId id, PlaylistId playlistId, TrackId trackId, TrackPlaylistPosition position) {
        this.id         = id;
        this.playlistId = playlistId;
        this.trackId    = trackId;
        this.position   = position;
    }

    public static TrackPlaylist create(PlaylistId playlistId, TrackId trackId, TrackPlaylistPosition position) {
        TrackPlaylist t = new TrackPlaylist(TrackPlaylistId.create(), playlistId, trackId, position);
        t.record(new TrackPlaylistCreatedDomainEvent());
        return t;
    }

    public TrackPlaylistId id() { return id; }

    public PlaylistId playlistId() { return playlistId; }

    public TrackId trackId() { return trackId; }

    public TrackPlaylistPosition position() { return position; }

    @Override
    public int hashCode() {
        return Objects.hash(id, playlistId, trackId, position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackPlaylist that = (TrackPlaylist) o;
        return id.equals(that.id) && playlistId.equals(that.playlistId) && trackId.equals(that.trackId) && position.equals(
                that.position);
    }
}
