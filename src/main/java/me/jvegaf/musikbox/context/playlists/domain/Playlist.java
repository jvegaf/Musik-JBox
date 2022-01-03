package me.jvegaf.musikbox.context.playlists.domain;

import me.jvegaf.musikbox.shared.domain.AggregateRoot;
import me.jvegaf.musikbox.shared.domain.playlist.PlaylistCreatedDomainEvent;
import me.jvegaf.musikbox.shared.domain.playlist.PlaylistUpdatedDomainEvent;

import java.util.UUID;

public final class Playlist extends AggregateRoot {

    private final PlaylistId   id;
    private final PlaylistName name;

    public Playlist(PlaylistId id, PlaylistName name) {
        this.id     = id;
        this.name   = name;
    }

    public Playlist() {
        this.id     = null;
        this.name   = null;
    }

    public static Playlist create(PlaylistName name) {
        PlaylistId id = new PlaylistId(UUID.randomUUID().toString());

        Playlist playlist = new Playlist(id, name);

        playlist.record(new PlaylistCreatedDomainEvent(id.value(), name.value()));

        return playlist;
    }

    public PlaylistId id() { return id; }

    public PlaylistName name() { return name; }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        Playlist p = (Playlist) obj;
        return id.equals(p.id()) && name.equals(p.name());
    }

    public Playlist update(PlaylistName newName) {
        Playlist p = new Playlist(this.id(), newName);
        p.record(new PlaylistUpdatedDomainEvent(this.id.value(), newName.value()));
        return p;
    }
}
