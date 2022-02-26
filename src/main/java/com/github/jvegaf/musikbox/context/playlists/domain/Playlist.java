package com.github.jvegaf.musikbox.context.playlists.domain;

import com.github.jvegaf.musikbox.shared.domain.AggregateRoot;
import com.github.jvegaf.musikbox.shared.domain.playlist.PlaylistCreatedDomainEvent;
import com.github.jvegaf.musikbox.shared.domain.playlist.PlaylistUpdatedDomainEvent;

import java.util.UUID;

public final class Playlist extends AggregateRoot {

    private final PlaylistId   id;
    private final PlaylistName name;

    public Playlist(PlaylistId id, PlaylistName name) {
        this.id   = id;
        this.name = name;
    }

    public Playlist() {
        this.id   = null;
        this.name = null;
    }

    public static Playlist create(PlaylistName name) {
        PlaylistId id = new PlaylistId(UUID.randomUUID()
                                           .toString());

        Playlist playlist = new Playlist(id, name);

        playlist.record(new PlaylistCreatedDomainEvent(id.value(), name.value()));

        return playlist;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;

        if (obj==null || getClass()!=obj.getClass()) return false;
        Playlist p = (Playlist) obj;
        if (!id.equals(p.id())) return false;
        assert name!=null;
        return name.equals(p.name());
    }

    public PlaylistId id() {return id;}

    public PlaylistName name() {return name;}

    public Playlist update(PlaylistName newName) {
        Playlist p = new Playlist(this.id(), newName);
        assert this.id!=null;
        p.record(new PlaylistUpdatedDomainEvent(this.id.value(), newName.value()));
        return p;
    }
}
