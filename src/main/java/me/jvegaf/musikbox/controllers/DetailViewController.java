package me.jvegaf.musikbox.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import me.jvegaf.musikbox.MainApp;
import me.jvegaf.musikbox.models.Track;

import java.io.ByteArrayInputStream;

public class DetailViewController {

    private Track track;
    private final MainApp parent;
    @FXML
    private ImageView artworkImageView;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField artistTextField;
    @FXML
    private TextField albumTextField;
    @FXML
    private TextField genreTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField bpmTextField;
    @FXML
    private TextArea commentsTextField;
    @FXML
    private TextField keyTextField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;


    public DetailViewController(MainApp parent) {
        this.parent = parent;
    }

    private void closeAction() {
        Stage stage = (Stage) this.cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void onSaveListener() {
        this.track.setTitle(this.titleTextField.getText());
        this.track.setArtist(this.artistTextField.getText());
        this.track.setAlbum(this.albumTextField.getText());
        this.track.setGenre(this.genreTextField.getText());
        this.track.setYear(this.yearTextField.getText());
        this.track.setBpm(this.bpmTextField.getText());
        this.track.setKey(this.keyTextField.getText());
        this.track.setComments(this.commentsTextField.getText());
        // TODO: implement this.track.setArtworkData
        this.parent.saveTags(this.track);
        this.closeAction();
    }

    private void initControls(Track t) {
        this.cancelBtn.setOnMouseClicked(event -> closeAction());
        this.saveBtn.setOnMouseClicked(event -> onSaveListener());
        this.titleTextField.setText(t.getTitle());
        this.artistTextField.setText(t.getArtist());
        this.albumTextField.setText(t.getAlbum());
        this.genreTextField.setText(t.getGenre());
        if (t.getYear() != null)
            this.yearTextField.setText(t.getYear().toString());
        if (t.getBpm() != null)
            this.bpmTextField.setText(String.valueOf(t.getBpm()));
        this.keyTextField.setText(t.getKey());
        this.commentsTextField.setText(t.getComments());

        if (t.getArtworkData().length < 1) {
            return;
        }

        this.artworkImageView.setImage(new Image(new ByteArrayInputStream(t.getArtworkData())));
    }

    public void setTrack(Track track) {
        this.track = track;
        initControls(track);
    }
}
