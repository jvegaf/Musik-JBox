package me.jvegaf.musikbox.context.trackplaylist.domain;


import me.jvegaf.musikbox.shared.domain.AggregateRoot;
import me.jvegaf.musikbox.shared.domain.track_playlist.TrackPlaylistCreatedDomainEvent;

public final class TrackPlaylist extends AggregateRoot {

    private final TrackPlaylistId id;
    private final String          playlistId;
    private final String          trackId;
    private final Integer         position;

    public TrackPlaylist() {
        this.id         = null;
        this.playlistId = null;
        this.trackId    = null;
        this.position   = null;
    }

    public TrackPlaylist(TrackPlaylistId id, String playlistId, String trackid, Integer position) {
        this.id         = id;
        this.playlistId = playlistId;
        this.trackId    = trackid;
        this.position   = position;
    }

    public static TrackPlaylist create(String playlistId, String trackId, Integer position) {
        TrackPlaylist t = new TrackPlaylist(TrackPlaylistId.create(), playlistId, trackId, position);
        t.record(new TrackPlaylistCreatedDomainEvent());
        return t;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;

        if (obj==null || getClass()!=obj.getClass()) return false;
        TrackPlaylist p = (TrackPlaylist) obj;
        return id.equals(p.id()) && trackId.equals(p.trackId()) && playlistId.equals(p.playlistId()) && position.equals(
                p.position());
    }

    public TrackPlaylistId id() {return id;}

    public String trackId() {return trackId;}

    public String playlistId() {return playlistId;}

    public Integer position() {return position;}
}
