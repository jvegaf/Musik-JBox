package me.jvegaf.musikbox.context.tracks.application.find;

import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackNotExist;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

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
