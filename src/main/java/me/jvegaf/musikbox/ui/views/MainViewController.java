package me.jvegaf.musikbox.ui.views;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.jvegaf.musikbox.MainApp;
import me.jvegaf.musikbox.services.MusicFileService;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;
import me.jvegaf.musikbox.ui.components.SideBar;
import me.jvegaf.musikbox.ui.components.Tracklist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainViewController {

  private MainApp parent;


  @FXML
  private SideBar sidebar;
  @FXML
  private Tracklist tracklist;
  @FXML
  private Label leftStatusLabel;
  @FXML
  private Label rightStatusLabel;


  private TracksRepository repository;

  @Inject
  private DetailViewController detailViewController;

  @Inject
  public MainViewController(TracksRepository repository) {
    this.repository = repository;
  }

  public void initialize() {
    autoloadTracks();
  }

  public void openActionListener() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedFolder = directoryChooser.showDialog(this.leftStatusLabel.getScene().getWindow());
    if (selectedFolder == null) return;
    System.out.println(selectedFolder.getAbsolutePath());
    ArrayList<Track> tracks = MusicFileService.processMusicFilesOfPath(selectedFolder);
    this.repository.addBatch(tracks);
  }


  private void autoloadTracks() {
    Dotenv dotenv = Dotenv.load();
    String devMusicPath = dotenv.get("DEV_MUSIC_PATH");
    this.repository.addBatch(MusicFileService.processMusicFilesOfPath(new File(devMusicPath)));
  }

  public void playActionListener(Track t) {
//    this.header.playTrack(t);
  }

  public void detailActionListener(Track t) {
    Stage detailStage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DetailView.fxml"));
    loader.setController(this.detailViewController);
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) return;
    Scene detailScene = new Scene(root, 563, 512);
    detailScene.getStylesheets().add("/styles/dark.css");
    detailViewController.setTrack(t);
    detailStage.setTitle("Song Detail");
    detailStage.setScene(detailScene);
    detailStage.initOwner(this.leftStatusLabel.getScene().getWindow());
    detailStage.initModality(Modality.APPLICATION_MODAL);
    detailStage.show();
  }

}
