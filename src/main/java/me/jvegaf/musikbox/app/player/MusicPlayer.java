package me.jvegaf.musikbox.app.player;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import me.jvegaf.musikbox.app.collection.MusicCollection;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.TrackResponse;

import java.io.File;
import java.util.Optional;

@Service
public final class MusicPlayer {

    public final  StringProperty                     titleProperty;
    public final  StringProperty                     artistProperty;
    public final  ObjectProperty<Duration>           currentPlayTimeProperty;
    public final  ObjectProperty<Duration>           totalDurationProperty;
    public final  ObjectProperty<MediaPlayer.Status> statusProperty;
    private final MusicCollection                    collection;
    private final TrackResponse                      currentTrack;
    private       MediaPlayer                        mPlayer;

    public MusicPlayer(MusicCollection collection) {
        this.collection              = collection;
        this.currentTrack            = null;
        this.titleProperty           = new SimpleStringProperty("");
        this.artistProperty          = new SimpleStringProperty("");
        this.currentPlayTimeProperty = new SimpleObjectProperty<>();
        this.totalDurationProperty   = new SimpleObjectProperty<>(Duration.UNKNOWN);
        this.statusProperty          = new SimpleObjectProperty<>(MediaPlayer.Status.UNKNOWN);
    }

    public void playNextTrack() {
        Optional<TrackResponse> t = Optional.ofNullable(collection.tracksProperty()
                                                                  .get(collection.tracksProperty()
                                                                                 .indexOf(currentTrack) + 1));
        if (t.isPresent()) {
            playTrack(t.get());
            return;
        }
        playTrack(collection.tracksProperty().get(0));
    }

    public void playTrack(TrackResponse track) {
        if (currentTrackChecker(track)) return;
        if (this.mPlayer!=null) stopTrack();
        var   path  = track.location();
        Media media = new Media(new File(path).toURI().toString());
        this.mPlayer = new MediaPlayer(media);
        this.mPlayer.play();
        setStatusProp();
        titleProperty.set(track.title());
        track.artist().ifPresent(this.artistProperty::setValue);
        this.totalDurationProperty.setValue(media.getDuration());
        setCurrentPlayTimeProp();
    }

    private boolean currentTrackChecker(TrackResponse track) {
        if (this.currentTrack==null) return false;
        return currentTrack.location().equals(track.location());
    }

    public void stopTrack() {
        this.mPlayer.dispose();
        this.mPlayer = null;
    }

    private void setStatusProp() {
        this.mPlayer.statusProperty().addListener((observable, oldValue, newValue) -> this.statusProperty.setValue(
                newValue));
    }

    private void setCurrentPlayTimeProp() {
        this.mPlayer.currentTimeProperty()
                    .addListener((observable, oldValue, newValue) -> this.currentPlayTimeProperty.setValue(newValue));
    }

    public void pauseTrack() {
        this.mPlayer.pause();
    }

    public void continuePlaying() {
        if (this.mPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) this.mPlayer.play();
    }

    public void seekTo(Duration value) {
        this.mPlayer.seek(value);
    }

    public MediaPlayer getMediaPlayer() {
        return this.mPlayer;
    }
}
