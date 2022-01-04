package me.jvegaf.musikbox.context.trackplaylist.application;

import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.shared.domain.bus.query.Response;

import java.util.List;

public final class TracksResponse implements Response {

    private final List<TrackResponse> tracks;

    public TracksResponse(List<TrackResponse> tracks) { this.tracks = tracks; }

    public List<TrackResponse> tracks() { return tracks; }
}
