package me.jvegaf.musikbox.context.trackplaylist.domain;


import me.jvegaf.musikbox.shared.domain.AggregateRoot;
import me.jvegaf.musikbox.shared.domain.track_playlist.TrackPlaylistCreatedDomainEvent;

public final class TrackPlaylist extends AggregateRoot {

    private final TrackPlaylistId id;
    private final String          playlistId;
    private final String          trackId;

    public TrackPlaylist() {
        this.id         = null;
        this.playlistId = null;
        this.trackId    = null;
    }

    public TrackPlaylist(TrackPlaylistId id, String playlistId, String trackid) {
        this.id         = id;
        this.playlistId = playlistId;
        this.trackId    = trackid;
    }

    public static TrackPlaylist create(String playlistId, String trackId) {
        TrackPlaylist t = new TrackPlaylist(TrackPlaylistId.create(), playlistId, trackId);
        t.record(new TrackPlaylistCreatedDomainEvent());
        return t;
    }

    public TrackPlaylistId id() { return id; }

    public String playlistId() { return playlistId; }

    public String trackId() { return trackId; }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        TrackPlaylist p = (TrackPlaylist) obj;
        return id.equals(p.id()) && trackId.equals(p.trackId()) && playlistId.equals(p.playlistId());
    }
}
