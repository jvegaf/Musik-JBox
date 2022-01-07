package me.jvegaf.musikbox.app.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import me.jvegaf.musikbox.app.collection.MusicCollection;


public final class PlaylistDetail {

    private MusicCollection collection;
    @FXML
    private Label           playlistNameLabel;
    @FXML
    private Label           playlistDetailsLabel;


    public void setCollection(MusicCollection collection) {
        this.collection = collection;
    }

    public void initBindings() {
        playlistNameLabel.textProperty().bind(collection.playListNameProperty());
        collection.collectionTracksCountProperty().addListener((observable, oldValue, newValue) -> {
            playlistDetailsLabel.setText("Total " + newValue.toString() + " tracks");
        });
    }
}
