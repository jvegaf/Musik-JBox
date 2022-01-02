package me.jvegaf.musikbox.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;

import java.io.IOException;

public class PlaylistCell extends ListCell<PlaylistResponse> {

    @FXML
    private Pane iconPane;

    @FXML
    private Label titleLbl;

    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader loader;

    @Override
    protected void updateItem(PlaylistResponse item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);

        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("PlaylistCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            titleLbl.setText(item.name());
            iconPane.getStyleClass().add("icon-playlist");

            setText(null);
            setGraphic(anchorPane);
        }
    }
}
