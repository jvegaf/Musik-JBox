package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.StringValueObject;

public class TrackArtist extends StringValueObject {

    public TrackArtist(String value) {
        super(value);
    }

    public TrackArtist() { super(""); }
}
