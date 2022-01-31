package com.github.jvegaf.musikbox.context.playlists.application.search_by_criteria;

import com.github.jvegaf.musikbox.context.playlists.application.PlaylistsResponse;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import com.github.jvegaf.musikbox.shared.domain.criteria.Filters;
import com.github.jvegaf.musikbox.shared.domain.criteria.Order;

@Service
public final class SearchPlaylistsByCriteriaQueryHandler implements QueryHandler<SearchPlaylistsByCriteriaQuery,
        PlaylistsResponse> {

    private final PlaylistsByCriteriaSearcher searcher;

    public SearchPlaylistsByCriteriaQueryHandler(PlaylistsByCriteriaSearcher searcher) {this.searcher = searcher;}


    @Override
    public PlaylistsResponse handle(SearchPlaylistsByCriteriaQuery query) {

        Filters filters = Filters.fromValues(query.filters());
        Order   order   = Order.fromValues(query.orderBy(), query.orderType());

        return searcher.search(filters, order, query.limit(), query.offset());
    }
}
