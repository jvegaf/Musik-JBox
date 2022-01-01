package me.jvegaf.musikbox.context.tracks.application.search_by_criteria;

import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import me.jvegaf.musikbox.shared.domain.criteria.Filters;
import me.jvegaf.musikbox.shared.domain.criteria.Order;
import org.springframework.stereotype.Service;

@Service
public final class SearchTracksByCriteriaQueryHandler implements QueryHandler<SearchTracksByCriteriaQuery,
        TracksResponse> {

    private final TracksByCriteriaSearcher searcher;

    public SearchTracksByCriteriaQueryHandler(TracksByCriteriaSearcher searcher) { this.searcher = searcher; }


    @Override public TracksResponse handle(SearchTracksByCriteriaQuery query) {

        Filters filters = Filters.fromValues(query.filters());
        Order order = Order.fromValues(query.orderBy(), query.orderType());

        return searcher.search(filters, order, query.limit(), query.offset());
    }
}
