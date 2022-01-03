package me.jvegaf.musikbox.context.playlists.domain;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.AggregateRoot;
import me.jvegaf.musikbox.shared.domain.playlist.PlaylistCreatedDomainEvent;
import me.jvegaf.musikbox.shared.domain.playlist.PlaylistUpdatedDomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Playlist extends AggregateRoot {

    private final PlaylistId   id;
    private final PlaylistName name;
    private final List<Track>  tracks;

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

        Playlist playlist = new Playlist(id, name, tracks);

        playlist.record(new PlaylistCreatedDomainEvent(id.value(), name.value()));

        return playlist;
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

    public Playlist update(PlaylistName newName) {
        Playlist p = new Playlist(this.id(), newName, this.tracks);
        p.record(new PlaylistUpdatedDomainEvent(this.id.value(), newName.value()));
        return p;
    }
}
