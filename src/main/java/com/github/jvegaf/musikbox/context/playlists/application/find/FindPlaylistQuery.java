package com.github.jvegaf.musikbox.context.playlists.application.find;

import com.github.jvegaf.musikbox.shared.domain.bus.query.Query;

public final class FindPlaylistQuery implements Query {

    private final String id;

    public FindPlaylistQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
