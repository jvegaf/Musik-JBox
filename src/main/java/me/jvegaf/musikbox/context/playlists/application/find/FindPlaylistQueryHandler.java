package me.jvegaf.musikbox.context.playlists.application.find;

import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistNotExist;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;

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
