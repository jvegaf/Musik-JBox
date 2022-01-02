package me.jvegaf.musikbox.context.playlists.application.search_all;

import me.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public final class SearchAllPlaylistsQueryHandler implements QueryHandler<SearchAllPlaylistsQuery, PlaylistsResponse> {

    private final AllPlaylistsSearcher searcher;

    public SearchAllPlaylistsQueryHandler(AllPlaylistsSearcher searcher) { this.searcher = searcher; }


    @Override
    public PlaylistsResponse handle(SearchAllPlaylistsQuery query) {
        return searcher.search();
    }
}
