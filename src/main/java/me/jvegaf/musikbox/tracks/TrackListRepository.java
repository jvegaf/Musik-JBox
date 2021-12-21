package me.jvegaf.musikbox.tracks;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TrackListRepository implements TracksRepository {


    private final ObservableList<Track> tracks;

    public TrackListRepository() {
        this.tracks = FXCollections.observableArrayList();
    }


    @Override
    public ObservableList<Track> getAll() {
        return this.tracks;
    }

    @Override
    public void add(Track track) {
        this.tracks.add(track);
    }

    @Override
    public void addBatch(List<Track> tracks) {
        this.tracks.clear();
        this.tracks.addAll(tracks);
    }

    @Override
    public void updateTrack(Track track) {
        this.tracks.set(this.tracks.indexOf(track), track);
    }

    @Override
    public void remove(String trackId) {
        this.tracks.remove(this.tracks.stream().filter(track -> track.getId().equals(trackId)).findFirst().get());
    }
}
