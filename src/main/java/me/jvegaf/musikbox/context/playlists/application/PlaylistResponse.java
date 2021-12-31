package me.jvegaf.musikbox.context.playlists.application;

import me.jvegaf.musikbox.context.playlists.domain.Playlist;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.bus.query.Response;

import java.util.List;

public final class PlaylistResponse implements Response {

    private final String  id;
    private final String  name;


    public PlaylistResponse(String id,
                            String name) {
        this.id       = id;
        this.name    = name;
    }

    public static PlaylistResponse fromAggregate(Playlist playlist) {
        return new PlaylistResponse(playlist.id().value(),
                                    playlist.name().value()
                                    );
    }

    public String id() { return id; }

    public String name() { return name; }
}
