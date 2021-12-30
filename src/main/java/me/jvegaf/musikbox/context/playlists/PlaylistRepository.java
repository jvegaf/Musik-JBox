package me.jvegaf.musikbox.context.playlists;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistRepository {

    private final ObservableList<Playlist> playlists;
    private final ObjectProperty<ObservableList<Playlist>> playlistsProperty;


    public PlaylistRepository() {
        this.playlists = FXCollections.observableArrayList();
        this.playlistsProperty = new SimpleObjectProperty<>(this.playlists);
    }

    public ObjectProperty<ObservableList<Playlist>> PlaylistsProperty() {
        return this.playlistsProperty;
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
        this.playlistsProperty.set(this.playlists);
    }
}
