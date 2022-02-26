package com.github.jvegaf.musikbox.context.trackplaylist.application.search;

import com.github.jvegaf.musikbox.context.trackplaylist.application.TrackPlaylistResponse;
import com.github.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylist;
import com.github.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistRepository;
import com.github.jvegaf.musikbox.context.tracks.application.TracksResponse;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackId;
import com.github.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.TrackResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public final class TrackPlaylistsSearcher {

    private final TrackPlaylistRepository repository;
    private final TrackRepository         trackRepository;


    public TrackPlaylistsSearcher(TrackPlaylistRepository repository, TrackRepository trackRepository) {
        this.repository      = repository;
        this.trackRepository = trackRepository;
    }

    public TracksResponse search(String playlistId) {
        List<TrackPlaylist> trackPlaylists = repository.search(playlistId);

        if (trackPlaylists.isEmpty()) return new TracksResponse(new ArrayList<>());

        List<TrackResponse> trackResponses = new ArrayList<>();
        for (TrackPlaylist tp : trackPlaylists) {
            TrackResponse
                    trackResponse =
                    TrackPlaylistResponse.fromAggregate(trackRepository.search(new TrackId(tp.trackId()))
                                                                       .orElseThrow(), tp.position());
            trackResponses.add(trackResponse);
        }

        return new TracksResponse(trackResponses);
    }
}
