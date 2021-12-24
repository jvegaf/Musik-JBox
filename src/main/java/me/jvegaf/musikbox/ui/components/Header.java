package me.jvegaf.musikbox.ui.components;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import me.jvegaf.musikbox.bus.CommandBus;
import me.jvegaf.musikbox.services.MusicFileService;
import me.jvegaf.musikbox.services.player.MusicPlayer;
import me.jvegaf.musikbox.tracks.Track;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public final class Header extends HBox implements Initializable {


    @FXML
    private Label artistLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button prevBtn;
    @FXML
    private Button playBtn;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button openFolderBtn;

    private final MusicPlayer player;
    private final CommandBus commandHandler;
    private Double duration;

    @Inject
    public Header(MusicPlayer player, CommandBus commandHandler) {
        this.player = player;
        this.commandHandler = commandHandler;
        URL resource = getClass().getResource("/components/Header.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disablePlayerControls(true);
        nextBtn.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playBtn.setOnMouseClicked(event -> this.player.continuePlaying());
        pauseBtn.setOnMouseClicked(event -> this.player.pauseTrack());
        prevBtn.setOnMouseClicked(event -> System.out.println("previous clicked !"));
        this.artistLabel.textProperty().bind(this.player.artistProperty);
        this.titleLabel.textProperty().bind(this.player.titleProperty);
        this.player.statusProperty.addListener((observable, oldValue, newValue) -> playerStatusHandler(newValue));
        openFolderBtn.setOnMouseClicked(event -> openActionListener());
        autoLoad();
    }

    private void disablePlayerControls(boolean value) {
        playBtn.setDisable(value);
        prevBtn.setDisable(value);
        nextBtn.setDisable(value);
    }

    private void initDisplayControls() {

        initProgressBar();
    }

    private void initProgressBar() {


        this.player.currentPlayTimeProperty.addListener((observable, oldValue, newValue) -> progressBar.setProgress((newValue.toMillis()/this.player.getMediaPlayer().getTotalDuration().toMillis())));

        progressBar.setOnMouseClicked(evt -> {
            double dx = evt.getX();
            double dwidth = progressBar.getWidth();
            double progression = (dx / dwidth);
            var milliseconds = (progression * this.player.getMediaPlayer().getTotalDuration().toMillis());
            Duration duration = new Duration(milliseconds);
            player.seekTo(duration);
        });
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

    private void openActionListener() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(openFolderBtn.getScene().getWindow());
        if (selectedFolder == null) return;
        System.out.println(selectedFolder.getAbsolutePath());
        ArrayList<Track> tracks = MusicFileService.processMusicFilesOfPath(selectedFolder);
        this.commandHandler.addBatch(tracks);
    }

    private void autoLoad() {
        String path = "//home//jose//Documents//CANELITA-PA-COLOCAR";
        ArrayList<Track> tracks = MusicFileService.processMusicFilesOfPath(new File(path));
        this.commandHandler.addBatch(tracks);
    }
}
