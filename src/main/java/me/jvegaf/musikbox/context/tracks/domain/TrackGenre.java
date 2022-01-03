package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.StringValueObject;

public class TrackGenre extends StringValueObject {

    public TrackGenre(String value) {
        super(value);
    }

    public TrackGenre() { super(""); }
}
