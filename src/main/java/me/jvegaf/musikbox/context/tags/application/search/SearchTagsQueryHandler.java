package me.jvegaf.musikbox.context.tags.application.search;

import me.jvegaf.musikbox.context.tags.application.TagResponse;
import me.jvegaf.musikbox.context.tags.domain.Tagger;
import me.jvegaf.musikbox.context.tracks.domain.TrackNotExist;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

@Service
public final class SearchTagsQueryHandler implements QueryHandler<SearchTagsQuery, TagResponse> {

    private final Tagger tagger;

    public SearchTagsQueryHandler(Tagger tagger) {
        this.tagger = tagger;
    }

    @Override
    public TagResponse handle(SearchTagsQuery query) throws TrackNotExist {
        return tagger.search(query.title(), query.artist(), query.duration());
    }
}
