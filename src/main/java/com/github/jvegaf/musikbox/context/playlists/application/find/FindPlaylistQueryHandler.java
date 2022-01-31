package com.github.jvegaf.musikbox.context.playlists.application.find;

import com.github.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistNotExist;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

@Service
public final class FindPlaylistQueryHandler implements QueryHandler<FindPlaylistQuery, PlaylistResponse> {

    private final PlaylistFinder finder;

    public FindPlaylistQueryHandler(PlaylistFinder finder) {
        this.finder = finder;
    }

    @Override
    public PlaylistResponse handle(FindPlaylistQuery query) throws PlaylistNotExist {
        return finder.find(new PlaylistId(query.id()));
    }
}
