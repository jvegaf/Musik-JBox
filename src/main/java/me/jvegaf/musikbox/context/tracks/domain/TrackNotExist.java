package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.DomainError;

public final class TrackNotExist extends DomainError {

    public TrackNotExist(TrackId id) {
        super("track_not_exist", String.format("Track with id <%s> does not exist", id.value()));
    }
}
