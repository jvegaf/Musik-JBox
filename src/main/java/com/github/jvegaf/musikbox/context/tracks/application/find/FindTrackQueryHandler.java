package com.github.jvegaf.musikbox.context.tracks.application.find;

import com.github.jvegaf.musikbox.context.tracks.domain.TrackId;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackNotExist;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.TrackResponse;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

@Service
public final class FindTrackQueryHandler implements QueryHandler<FindTrackQuery, TrackResponse> {

    private final TrackFinder finder;

    public FindTrackQueryHandler(TrackFinder finder) {
        this.finder = finder;
    }

    @Override
    public TrackResponse handle(FindTrackQuery query) throws TrackNotExist {
        return finder.find(new TrackId(query.id()));
    }
}
