package me.jvegaf.musikbox.context.playlists.domain;

import me.jvegaf.musikbox.shared.domain.Identifier;

import java.util.UUID;

public class PlaylistId extends Identifier {

    public PlaylistId(String id) {
        super(id);
    }

    public PlaylistId() { }


    public static PlaylistId create() {
        // FIXME: This is a temporary solution
        return new PlaylistId(UUID.randomUUID().toString());
    }
}
