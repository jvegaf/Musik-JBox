package com.github.jvegaf.musikbox.app.controller;

import com.github.jvegaf.musikbox.app.player.MusicPlayer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.log4j.Log4j2;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@FxmlView
public final class HeaderController {


    private final MusicPlayer player;
    @FXML
    public        Button      closeBtn;
    @FXML
    public        Button      minimizeBtn;
    @FXML
    public        Button      maximizeBtn;
    @FXML
    private       ImageView   artView;
    @FXML
    private       Label       artistLabel;
    @FXML
    private       Label       titleLabel;
    @FXML
    private       Label       currentTimeLabel;
    @FXML
    private       Label       remainTimeLabel;
    @FXML
    private       ProgressBar progressBar;
    @FXML
    private       Button      prevBtn;
    @FXML
    private       Button      playBtn;
    @FXML
    private       Button      pauseBtn;
    @FXML
    private       Button      nextBtn;

    @Autowired
    public HeaderController(MusicPlayer player) {
        this.player = player;
    }

    @FXML
    public void initialize() {
        disablePlayerControls(true);
        closeBtn.setOnAction(event -> Platform.exit());
        minimizeBtn.setOnAction(event -> ((Stage) ((Button) event.getSource()).getScene()
                                                                              .getWindow()).setIconified(true));
        maximizeBtn.setOnAction(event -> ((Stage) ((Button) event.getSource()).getScene()
                                                                              .getWindow()).setMaximized(winMaximized()));
        nextBtn.setOnMouseClicked(event -> player.playNextTrack());
        playBtn.setOnMouseClicked(event -> player.continuePlaying());
        pauseBtn.setOnMouseClicked(event -> player.pauseTrack());
        prevBtn.setOnMouseClicked(event -> System.out.println("previous clicked !"));
        artistLabel.textProperty()
                   .bind(player.artistProperty);
        titleLabel.textProperty()
                  .bind(player.titleProperty);
        player.statusProperty.addListener((observable, oldValue, newValue) -> playerStatusHandler(newValue));
        player.artworkProperty.addListener((observable, oldValue, newValue) -> artView.setImage(newValue));
    }

    private void disablePlayerControls(boolean value) {
        playBtn.setDisable(value);
        prevBtn.setDisable(value);
        nextBtn.setDisable(value);
    }

    private boolean winMaximized() {
        return !((Stage) (maximizeBtn.getScene()
                                     .getWindow())).isMaximized();
    }

    private void playerStatusHandler(MediaPlayer.Status status) {
        switch (status) {
            case UNKNOWN -> {
                System.out.println("UNKNOWN STATE");
                disablePlayerControls(true);
            }
            case READY -> {
                disablePlayerControls(false);
                initDisplayControls();
            }
            case PAUSED, STOPPED -> {
                playBtn.setVisible(true);
                pauseBtn.setVisible(false);
            }
            case PLAYING -> {
                playBtn.setVisible(false);
                pauseBtn.setVisible(true);
            }
            default -> {
            }
        }
    }


    private void initDisplayControls() {

        initProgressBar();
        initDisplayLabels();
    }

    private void initProgressBar() {


        this.player.currentPlayTimeProperty.addListener((observable, oldValue, newValue) -> progressBar.setProgress((newValue.toMillis() /
                                                                                                                     player.getMediaPlayer()
                                                                                                                           .getTotalDuration()
                                                                                                                           .toMillis())));

        progressBar.setOnMouseClicked(evt -> {
            double dx          = evt.getX();
            double dwidth      = progressBar.getWidth();
            double progression = (dx / dwidth);
            var
                    milliseconds =
                    (progression *
                     player.getMediaPlayer()
                           .getTotalDuration()
                           .toMillis());
            Duration duration = new Duration(milliseconds);
            player.seekTo(duration);
        });
    }

    private void initDisplayLabels() {
        currentTimeLabel.textProperty()
                        .bind(Bindings.createStringBinding(() -> String.format("%.0f:%02.0f",
                                                                               player.getMediaPlayer()
                                                                                     .getCurrentTime()
                                                                                     .toMinutes(),
                                                                               player.getMediaPlayer()
                                                                                     .getCurrentTime()
                                                                                     .toSeconds() % 60),
                                                           player.getMediaPlayer()
                                                                 .currentTimeProperty()));

        remainTimeLabel.textProperty()
                       .bind(Bindings.createStringBinding(() -> String.format("%.0f:%02.0f",
                                                                              player.getMediaPlayer()
                                                                                    .getCurrentTime()
                                                                                    .toMinutes() -
                                                                              player.getMediaPlayer()
                                                                                    .getTotalDuration()
                                                                                    .toMinutes(),
                                                                              (player.getMediaPlayer()
                                                                                     .getTotalDuration()
                                                                                     .toSeconds() -
                                                                               player.getMediaPlayer()
                                                                                     .getCurrentTime()
                                                                                     .toSeconds()) % 60),
                                                          player.getMediaPlayer()
                                                                .currentTimeProperty()));
    }
}
