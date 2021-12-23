package me.jvegaf.musikbox.ui.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import me.jvegaf.musikbox.tracks.Track;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class Player extends AnchorPane implements Initializable {

    @FXML
    private Button pauseBtn;
    @FXML
    private Button prevBtn;
    @FXML
    private Button playBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Label artistLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Slider progressBar;

    private Track currentTrack;
    private MediaPlayer mPlayer;
    private StringProperty artistProperty;
    private StringProperty titleProperty;


    public Player() {
        URL resource = getClass().getResource("/components/Player.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.artistProperty = new SimpleStringProperty("");
        this.titleProperty = new SimpleStringProperty("");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nextBtn.setDisable(this.mPlayer == null);
        prevBtn.setDisable(this.mPlayer == null);
        pauseBtn.setDisable(this.mPlayer == null);
        playBtn.setDisable(this.mPlayer == null);
        nextBtn.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playBtn.setOnMouseClicked(event -> this.mPlayer.play());
        pauseBtn.setOnMouseClicked(event -> this.mPlayer.pause());
        prevBtn.setOnMouseClicked(event -> System.out.println("previous clicked !"));
    }

    public void playTrack(Track track) {
        if (track.equals(this.currentTrack)) return;
        String filepath = track.getPath();
        if (this.mPlayer != null) this.mPlayer.dispose();
        Media media = new Media(new File(filepath).toURI().toString());
        this.mPlayer = new MediaPlayer(media);
        this.mPlayer.play();
        this.currentTrack = track;
        this.titleProperty.setValue(track.getTitle());
        this.artistProperty.setValue(track.getArtist());
        artistLabel.textProperty().bind(this.artistProperty);
        titleLabel.textProperty().bind(this.titleProperty);
    }
}
