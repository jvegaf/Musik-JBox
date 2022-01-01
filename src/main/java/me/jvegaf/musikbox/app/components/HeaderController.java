package me.jvegaf.musikbox.app.components;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.player.MusicPlayer;
import me.jvegaf.musikbox.context.tracks.infrastructure.file.CollectFilesCommand;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
@Component
@FxmlView()
public final class HeaderController {


    @FXML
    private Label artistLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Label remainTimeLabel;
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
    public Button openFolderBtn;

    private Double duration;

    private MusicPlayer player;

    private CommandBus bus;

    @Autowired
    public HeaderController(MusicPlayer player, CommandBus bus) {
        this.player = player;
        this.bus    = bus;
    }

    @FXML
    public void initialize() {
        disablePlayerControls(true);
        nextBtn.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playBtn.setOnMouseClicked(event -> this.player.continuePlaying());
        pauseBtn.setOnMouseClicked(event -> this.player.pauseTrack());
        prevBtn.setOnMouseClicked(event -> System.out.println("previous clicked !"));
        this.artistLabel.textProperty().bind(this.player.artistProperty);
        this.titleLabel.textProperty().bind(this.player.titleProperty);
        this.player.statusProperty.addListener((observable, oldValue, newValue) -> playerStatusHandler(newValue));
        openFolderBtn.setOnAction(event -> openActionListener());
    }


    private void disablePlayerControls(boolean value) {
        playBtn.setDisable(value);
        prevBtn.setDisable(value);
        nextBtn.setDisable(value);
    }

    private void initDisplayControls() {

        initProgressBar();
        currentTimeLabel.textProperty()
                        .bind(Bindings.createStringBinding(() -> String.format("%.0f:%02.0f", player
                                                                   .getMediaPlayer()
                                                                   .getCurrentTime()
                                                                   .toMinutes(), player
                                                                                       .getMediaPlayer()
                                                                                       .getCurrentTime()
                                                                                       .toSeconds() % 60),
                                                           player.getMediaPlayer().currentTimeProperty()));

        remainTimeLabel.textProperty()
                       .bind(Bindings.createStringBinding(() -> String.format("%.0f:%02.0f", player
                               .getMediaPlayer()
                               .getCurrentTime()
                               .toMinutes() - player
                               .getMediaPlayer()
                               .getTotalDuration()
                               .toMinutes(), (player
                               .getMediaPlayer()
                               .getTotalDuration()
                               .toSeconds() - player
                               .getMediaPlayer()
                               .getCurrentTime()
                               .toSeconds()) % 60), player.getMediaPlayer().currentTimeProperty()));
    }

    private void initProgressBar() {


        this.player.currentPlayTimeProperty
                .addListener((observable, oldValue, newValue) -> progressBar
                        .setProgress((newValue.toMillis() / this.player
                                .getMediaPlayer().getTotalDuration().toMillis())));

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
        log.info("Open folder action");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(openFolderBtn.getScene().getWindow());
        if (selectedFolder == null) return;
        this.bus.dispatch(new CollectFilesCommand(selectedFolder));
    }
}
