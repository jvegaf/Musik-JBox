package me.jvegaf.musikbox.tracks;

import javafx.collections.ObservableList;

import java.util.List;


public interface TracksRepository {
    ObservableList<Track> getAll();
    void add(Track track);
    void addBatch(List<Track> tracks);

    void updateTrack(Track track);

    void remove(String trackId);
}
