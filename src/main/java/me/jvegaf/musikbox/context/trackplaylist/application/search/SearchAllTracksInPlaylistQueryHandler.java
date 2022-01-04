package me.jvegaf.musikbox.context.trackplaylist.application.search;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.trackplaylist.application.TracksResponse;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

@Service
public final class SearchAllTracksInPlaylistQueryHandler implements QueryHandler<SearchAllTracksInPlaylistQuery,
        TracksResponse> {

    private final TrackPlaylistsSearcher searcher;

    public SearchAllTracksInPlaylistQueryHandler(TrackPlaylistsSearcher searcher) { this.searcher = searcher; }


    @Override
    public TracksResponse handle(SearchAllTracksInPlaylistQuery query) {
        return searcher.search(new PlaylistId(query.playlistId()));
    }
}
