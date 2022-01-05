package me.jvegaf.musikbox.context.trackplaylist.application.search;

import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylist;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistRepository;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.Service;

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

        List<Track>
                tracks =
                trackPlaylists.stream()
                              .map(tp -> trackRepository.search(new TrackId(tp.trackId())).orElseThrow())
                              .toList();

        List<TrackResponse> trackResponses = tracks.stream().map(TrackResponse::fromAggregate).toList();

        return new TracksResponse(trackResponses);
    }
}
