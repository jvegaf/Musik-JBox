package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.DurationValueObject;

public final class TrackDuration extends DurationValueObject {

    public TrackDuration(Integer value) {
        super(value);
    }

    public TrackDuration(String value) {
        super(value);
    }

    public TrackDuration() {}
}
