package com.github.jvegaf.musikbox.context.tracks.application.find;

import com.github.jvegaf.musikbox.shared.domain.bus.query.Query;

public final class FindTrackQuery implements Query {
    private final String id;

    public FindTrackQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
