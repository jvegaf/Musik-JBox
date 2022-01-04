package me.jvegaf.musikbox.context.trackplaylist.domain;

import me.jvegaf.musikbox.shared.domain.IntValueObject;

public final class TrackPlaylistPosition extends IntValueObject {

    public TrackPlaylistPosition(Integer value) {
        super(value);
    }

    public TrackPlaylistPosition() {
        super(null);
    }
}
