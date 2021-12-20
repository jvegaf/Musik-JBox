package me.jvegaf.musikbox.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.jvegaf.musikbox.models.Track;

import java.util.ArrayList;

public class LibraryService {
    private ObservableList<Track> tracks;

    public LibraryService() {
        this.tracks = FXCollections.observableArrayList();
    }

    public ObservableList<Track> getTracks() {
        return tracks;
    }

    public void addTracks(ArrayList<Track> tracks) {
        this.tracks.addAll(tracks);
    }

    public void updateTrack(Track t) {
        int idx = this.tracks.indexOf(t);
        this.tracks.set(idx, t);
    }
}
