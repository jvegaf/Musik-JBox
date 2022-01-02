package me.jvegaf.musikbox.context.playlists.application.find;

import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistNotExist;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

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
