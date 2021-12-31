package me.jvegaf.musikbox.context.playlists.infrastructure.persistence;

import me.jvegaf.musikbox.context.playlists.domain.Playlist;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackRepository;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public final class InMemoryPlaylistRepository implements PlaylistRepository {

    private final List<Playlist> playlists = new ArrayList<>();

    @Override
    public void save(Playlist playlist) {
        this.playlists.add(playlist);
    }

    @Override
    public List<Playlist> searchAll() {
        return playlists;
    }

    @Override
    public Optional<Playlist> find(PlaylistId id) {
        return playlists.stream().filter(playlist -> playlist.id().equals(id)).findFirst();
    }

    @Override
    public List<Playlist> matching(Criteria criteria) {
        return null;
    }

}
