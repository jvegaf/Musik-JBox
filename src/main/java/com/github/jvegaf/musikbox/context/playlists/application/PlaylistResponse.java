package com.github.jvegaf.musikbox.context.playlists.application;

import com.github.jvegaf.musikbox.context.playlists.domain.Playlist;
import com.github.jvegaf.musikbox.shared.domain.bus.query.Response;

public final class PlaylistResponse implements Response {

    private final String id;
    private final String name;


    public PlaylistResponse(
            String id, String name
    ) {
        this.id   = id;
        this.name = name;
    }

    public static PlaylistResponse fromAggregate(Playlist playlist) {
        return new PlaylistResponse(playlist.id()
                                            .value(),
                                    playlist.name()
                                            .value());
    }

    public String id() {return id;}

    public String name() {return name;}
}
