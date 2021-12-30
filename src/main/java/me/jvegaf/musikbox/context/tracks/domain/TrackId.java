package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.Identifier;

import java.util.UUID;

public class TrackId extends Identifier {

    public TrackId(String id) {
        super(id);
    }


    public static TrackId create() {
        // FIXME: This is a temporary solution
        return new TrackId(UUID.randomUUID().toString());
    }
}
