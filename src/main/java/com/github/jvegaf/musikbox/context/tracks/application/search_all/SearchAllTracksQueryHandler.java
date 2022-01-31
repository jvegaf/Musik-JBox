package com.github.jvegaf.musikbox.context.tracks.application.search_all;

import com.github.jvegaf.musikbox.context.tracks.application.TracksResponse;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

@Service
public final class SearchAllTracksQueryHandler implements QueryHandler<SearchAllTracksQuery, TracksResponse> {

    private final AllTracksSearcher searcher;

    public SearchAllTracksQueryHandler(AllTracksSearcher searcher) {this.searcher = searcher;}


    @Override
    public TracksResponse handle(SearchAllTracksQuery query) {
        return searcher.search();
    }
}
