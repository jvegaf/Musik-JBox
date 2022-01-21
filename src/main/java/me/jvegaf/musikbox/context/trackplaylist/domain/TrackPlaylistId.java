package me.jvegaf.musikbox.context.trackplaylist.domain;

import me.jvegaf.musikbox.shared.domain.Identifier;

import java.util.UUID;

public class TrackPlaylistId extends Identifier {

    public TrackPlaylistId(String id) {
        super(id);
    }

    public TrackPlaylistId() {}


    public static TrackPlaylistId create() {
        // FIXME: This is a temporary solution
        return new TrackPlaylistId(UUID.randomUUID().toString());
    }
}
