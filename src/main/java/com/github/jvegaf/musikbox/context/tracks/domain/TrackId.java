package com.github.jvegaf.musikbox.context.tracks.domain;

import com.github.jvegaf.musikbox.shared.domain.Identifier;

import java.util.UUID;

public class TrackId extends Identifier {

    public TrackId(String id) {
        super(id);
    }

    public TrackId() {}


    public static TrackId create() {
        // FIXME: This is a temporary solution
        return new TrackId(UUID.randomUUID()
                               .toString());
    }
}
