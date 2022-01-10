package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.StringValueObject;

public class TrackTitle extends StringValueObject {

    public TrackTitle(String value) {
        super(value);
    }

    public TrackTitle() {super("");}
}
