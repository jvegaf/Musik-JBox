package me.jvegaf.musikbox.context.playlists.application.search_by_criteria;

import me.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import me.jvegaf.musikbox.shared.domain.criteria.Filters;
import me.jvegaf.musikbox.shared.domain.criteria.Order;
import org.springframework.stereotype.Service;

@Service
public final class SearchPlaylistsByCriteriaQueryHandler implements QueryHandler<SearchPlaylistsByCriteriaQuery, PlaylistsResponse> {

    private final PlaylistsByCriteriaSearcher searcher;

    public SearchPlaylistsByCriteriaQueryHandler(PlaylistsByCriteriaSearcher searcher) { this.searcher = searcher; }


    @Override public PlaylistsResponse handle(SearchPlaylistsByCriteriaQuery query) {

        Filters filters = Filters.fromValues(query.filters());
        Order order = Order.fromValues(query.orderBy(), query.orderType());

        return searcher.search(filters, order, query.limit(), query.offset());
    }
}
