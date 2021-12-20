package me.jvegaf.musikbox.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import me.jvegaf.musikbox.services.PlayerService;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

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
    @FXML
    private Button openFolderBtn;

    private MainViewController mainViewController;
    private PlayerService playerService;
    private Double trackDuration;

    public void injectDeeps(MainViewController mvController, PlayerService playerService) {
        this.mainViewController = mvController;
        this.playerService = playerService;
        initListeners();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        downDisplayControls();
        initActionBtns();
    }

    private void downDisplayControls() {
        progressBar.setVisible(false);
        pauseBtn.setVisible(false);
        disablePlayerControls(true);
    }

    private void disablePlayerControls(boolean value) {
        playBtn.setDisable(value);
        prevBtn.setDisable(value);
        nextBtn.setDisable(value);
    }

    private void initActionBtns() {
        nextBtn.setOnMouseClicked(event -> System.out.println("next clicked !"));
        playBtn.setOnMouseClicked(event -> this.playerService.continuePlaying());
        pauseBtn.setOnMouseClicked(event -> this.playerService.pauseTrack());
        prevBtn.setOnMouseClicked(event -> System.out.println("previous clicked !"));
        openFolderBtn.setOnMouseClicked(event -> this.mainViewController.onOpenFolder());
    }


    private void initListeners() {
        this.playerService.titleProperty.addListener((observable, oldValue, newValue) -> this.titleLabel.setText(newValue));
        this.playerService.artistProperty.addListener((observable, oldValue, newValue) -> this.artistLabel.setText(newValue));
        this.playerService.totalDuration.addListener((observable, oldValue, newValue) -> this.trackDuration = newValue.toSeconds());
        this.playerService.statusProperty.addListener((observable, oldValue, newValue) -> playerStatusHandler(newValue));
    }

    private void initDisplayControls() {
        progressBar.setVisible(true);
        initProgressBar();
    }

    private void initProgressBar() {
        this.playerService.currentPlayTimeProperty.addListener((observable, oldValue, newValue) -> this.progressBar.setValue((newValue.toSeconds() / this.trackDuration) * 100));
        this.progressBar.valueProperty().addListener(observable -> {
            if (this.progressBar.isValueChanging()){
                this.playerService.seekTo((this.progressBar.getValue() / 100) * this.trackDuration);
            }
        });
    }

    private void playerStatusHandler(MediaPlayer.Status status){
        switch (status){
            case UNKNOWN:
                System.out.println("UNKNOWN STATE");
                disablePlayerControls( true);
                break;
            case READY:
                disablePlayerControls(false);
                initDisplayControls();
                break;
            case PAUSED:
            case STOPPED:
                playBtn.setVisible(true);
                pauseBtn.setVisible(false);
                break;
            case PLAYING:
                playBtn.setVisible(false);
                pauseBtn.setVisible(true);
                break;
            default:
                break;
        }
    }

}
