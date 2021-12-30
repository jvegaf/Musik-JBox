package me.jvegaf.musikbox.context.tracks;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.jvegaf.musikbox.context.tracks.domain.Track;

import java.util.List;

public class OldTracksRepository {


    private final ObservableList<Track>                 tracks;
    private final ObjectProperty<ObservableList<Track>> tracksProperty;

    public OldTracksRepository() {
        this.tracks         = FXCollections.observableArrayList();
        this.tracksProperty = new SimpleObjectProperty<>();
    }


    public void add(Track track) {
        this.tracks.add(track);
    }


    public void addBatch(List<Track> tracks) {
        this.tracks.removeAll();
        this.tracks.addAll(tracks);
        this.tracksProperty.setValue(this.tracks);
    }


    public void updateTrack(Track track) {
        this.tracks.set(this.tracks.indexOf(track), track);
    }


    public void remove(String trackId) {
        this.tracks.remove(this.tracks.stream().filter(track -> track.getId().equals(trackId)).findFirst().orElseThrow());
    }

    private ObservableList<Track> getTracksProperty() {
        return tracksProperty.get();
    }

    public ObjectProperty<ObservableList<Track>> tracksObjectProperty() {
        return tracksProperty;
    }
}
