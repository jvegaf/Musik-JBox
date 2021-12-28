package me.jvegaf.musikbox.playlists;

import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Playlist {
    private final String id;
    private final String name;
    private List<Track> tracks;

    public Playlist(String id ,String name, List<Track> tracks) {
        this.id = id;
        this.name = name;
        this.tracks = tracks;
    }

    public static Playlist createPlaylist(String name) {
        String id = UUID.randomUUID().toString();
        List<Track> tracks = new ArrayList<>();
        return new Playlist(id, name, tracks);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
