package me.jvegaf.musikbox.context.playlists.domain;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Playlist extends AggregateRoot {

    private final PlaylistId   id;
    private final PlaylistName name;
    private       List<Track>  tracks;

    public Playlist(PlaylistId id, PlaylistName name, List<Track> tracks) {
        this.id     = id;
        this.name   = name;
        this.tracks = tracks;
    }

    private Playlist() {
        this.id     = null;
        this.name   = null;
        this.tracks = null;
    }

    public static Playlist create(PlaylistName name) {
        PlaylistId id = new PlaylistId(UUID.randomUUID().toString());
        List<Track> tracks = new ArrayList<>();
        return new Playlist(id, name, tracks);
    }

    public PlaylistId id() { return id; }

    public PlaylistName name() { return name; }

    public List<Track> tracks() { return tracks; }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        Playlist p = (Playlist) obj;
        return id.equals(p.id()) && name.equals(p.name()) && tracks.equals(p.tracks());
    }
}
