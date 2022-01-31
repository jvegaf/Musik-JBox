package com.github.jvegaf.musikbox.context.trackplaylist.application.search;

import com.github.jvegaf.musikbox.shared.domain.bus.query.Query;

public final class SearchAllTracksInPlaylistQuery implements Query {

    private final String playlistId;

    public SearchAllTracksInPlaylistQuery(String playlistId) {this.playlistId = playlistId;}

    public String playlistId() {return playlistId;}
}
