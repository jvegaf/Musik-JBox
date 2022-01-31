package com.github.jvegaf.musikbox.context.tags.application.search;

import com.github.jvegaf.musikbox.context.tags.application.TagResponse;
import com.github.jvegaf.musikbox.context.tags.domain.Tagger;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackNotExist;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

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
