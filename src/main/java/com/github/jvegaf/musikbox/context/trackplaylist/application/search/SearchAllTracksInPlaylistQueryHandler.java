package com.github.jvegaf.musikbox.context.trackplaylist.application.search;

import com.github.jvegaf.musikbox.context.tracks.application.TracksResponse;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

@Service
public final class SearchAllTracksInPlaylistQueryHandler implements QueryHandler<SearchAllTracksInPlaylistQuery,
        TracksResponse> {

    private final TrackPlaylistsSearcher searcher;

    public SearchAllTracksInPlaylistQueryHandler(TrackPlaylistsSearcher searcher) {this.searcher = searcher;}


    @Override
    public TracksResponse handle(SearchAllTracksInPlaylistQuery query) {
        return searcher.search(query.playlistId());
    }
}
