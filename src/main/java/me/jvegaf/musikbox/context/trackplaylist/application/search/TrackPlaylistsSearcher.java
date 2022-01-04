package me.jvegaf.musikbox.context.trackplaylist.application.search;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.trackplaylist.application.TracksResponse;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylist;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistRepository;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class TrackPlaylistsSearcher {

    private final TrackPlaylistRepository repository;
    private final TrackRepository         trackRepository;


    public TrackPlaylistsSearcher(TrackPlaylistRepository repository, TrackRepository trackRepository) {
        this.repository      = repository;
        this.trackRepository = trackRepository;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public TracksResponse search(PlaylistId playlistId) {
        new ArrayList<>();
        List<TrackPlaylist> trackPlaylists = repository.search(playlistId);

        return new TracksResponse(trackPlaylists.stream()
                                                .map(tp -> trackRepository.search(tp.trackId()).get())
                                                .map(TrackResponse::fromAggregate)
                                                .collect(Collectors.toList()));
    }
}
