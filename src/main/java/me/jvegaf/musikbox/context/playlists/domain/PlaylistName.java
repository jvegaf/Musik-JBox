package me.jvegaf.musikbox.context.playlists.domain;

import me.jvegaf.musikbox.shared.domain.StringValueObject;

public class PlaylistName extends StringValueObject {

    public PlaylistName(String value) {
        super(value);
    }

    public PlaylistName() {super("");}
}
