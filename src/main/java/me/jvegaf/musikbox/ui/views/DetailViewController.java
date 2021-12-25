package me.jvegaf.musikbox.ui.views;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;

import java.io.ByteArrayInputStream;

public class DetailViewController {

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


    private final TracksRepository tracksRepository;

    @Inject
    public DetailViewController(TracksRepository tracksRepository) {
        this.tracksRepository = tracksRepository;
    }

    private void closeActionListener() {
        Stage stage = (Stage) this.cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void saveActionListener(Track t) {

        t.setArtist(this.artistTextField.getText());
        t.setName(this.titleTextField.getText());
        t.setAlbum(this.albumTextField.getText());
        t.setGenre(this.genreTextField.getText());
        t.setYear(this.yearTextField.getText());
        t.setBpm(this.bpmTextField.getText());
        t.setKey(this.keyTextField.getText());
        t.setComments(this.commentsTextField.getText());
        // TODO: implement this.track.setArtworkData
        this.tracksRepository.updateTrack(t);
        this.closeActionListener();
    }

    public void setTrack(Track track) {
        this.cancelBtn.setOnMouseClicked(event -> closeActionListener());
        this.saveBtn.setOnMouseClicked(event -> saveActionListener(track));
        this.titleTextField.setText(track.getName());
        this.artistTextField.setText(track.getArtist());
        this.albumTextField.setText(track.getAlbum());
        this.genreTextField.setText(track.getGenre());
        this.yearTextField.setText(track.getYear());
        if (track.getBpm() != null)
            this.bpmTextField.setText(String.valueOf(track.getBpm()));
        this.keyTextField.setText(track.getKey());
        this.commentsTextField.setText(track.getComments());

        if (track.getArtworkData().length < 1) {
            return;
        }

        this.artworkImageView.setImage(new Image(new ByteArrayInputStream(track.getArtworkData())));
    }
}
