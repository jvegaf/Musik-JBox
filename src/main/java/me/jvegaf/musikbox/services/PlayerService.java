package me.jvegaf.musikbox.services;

import javafx.beans.property.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import me.jvegaf.musikbox.tracks.Track;

import java.io.File;

public class PlayerService {
    private String path = "";
    private MediaPlayer mPlayer;
    public StringProperty titleProperty;
    public StringProperty artistProperty;
    public ObjectProperty<Duration> currentPlayTimeProperty;
    public ObjectProperty<Duration> totalDuration;
    public ObjectProperty<MediaPlayer.Status> statusProperty;

    public PlayerService() {
        this.titleProperty = new SimpleStringProperty("");
        this.artistProperty = new SimpleStringProperty("");
        this.currentPlayTimeProperty = new SimpleObjectProperty<>();
        this.totalDuration = new SimpleObjectProperty<>();
        this.statusProperty = new SimpleObjectProperty<>();
    }

    public void playTrack(Track track) {
        String filepath = track.getPath();
        if (!ensureIsDifferentFile(filepath)) return;
        if (this.mPlayer != null) this.mPlayer.dispose();
        this.path = track.getPath();
        Media media = new Media(new File(filepath).toURI().toString());
        this.mPlayer = new MediaPlayer(media);
        this.mPlayer.play();
        this.statusProperty.bind(this.mPlayer.statusProperty());
        this.titleProperty.setValue(track.getTitle());
        this.artistProperty.setValue(track.getArtist());
        this.totalDuration.bind(this.mPlayer.totalDurationProperty());
        this.currentPlayTimeProperty.bind(this.mPlayer.currentTimeProperty());
    }

    public void pauseTrack() {
        this.mPlayer.pause();
    }

    private boolean ensureIsDifferentFile(String filepath) {
        return !this.path.equals(filepath);
    }

    public void continuePlaying() {
        if (this.mPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) this.mPlayer.play();
    }

    public void seekTo(double value) {
        this.mPlayer.seek(Duration.seconds(value));
    }

    public void stopTrack() {
        this.mPlayer.dispose();
        this.mPlayer = null;
    }
}
