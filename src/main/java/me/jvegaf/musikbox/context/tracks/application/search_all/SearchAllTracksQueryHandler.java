package me.jvegaf.musikbox.context.tracks.application.search_all;

import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public final class SearchAllTracksQueryHandler implements QueryHandler<SearchAllTracksQuery, TracksResponse> {

    private final AllTracksSearcher searcher;

    public SearchAllTracksQueryHandler(AllTracksSearcher searcher) { this.searcher = searcher; }


    @Override public TracksResponse handle(SearchAllTracksQuery query) {
        return searcher.search();
    }
}
