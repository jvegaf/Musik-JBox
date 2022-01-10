package me.jvegaf.musikbox.app.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    private TextField titleTF;

    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader loader;


    @Override
    protected void updateItem(PlaylistResponse item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item==null) {
            setText(null);
            setGraphic(null);

        }
        else {
            if (loader==null) {
                loader = new FXMLLoader(getClass().getResource("/fxml/PlaylistCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            titleTF.setVisible(false);
            titleLbl.setText(item.name());
            iconPane.getStyleClass().add("icon-playlist");

            setText(null);
            setGraphic(anchorPane);
        }
    }


    @Override
    public void startEdit() {
        super.startEdit();
        titleTF.setText(titleLbl.getText());
        titleTF.setVisible(true);
        titleLbl.setVisible(false);
        Platform.runLater(() -> titleTF.requestFocus());
        titleTF.setOnKeyReleased(event -> {
            if (event.getCode()==KeyCode.ENTER) commitEdit(new PlaylistResponse(getItem().id(), titleTF.getText()));
        });
    }

    @Override
    public void commitEdit(PlaylistResponse newValue) {
        super.commitEdit(newValue);
        titleTF.setVisible(false);
        titleLbl.setVisible(true);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        titleTF.setVisible(false);
        titleLbl.setVisible(true);
    }
}
