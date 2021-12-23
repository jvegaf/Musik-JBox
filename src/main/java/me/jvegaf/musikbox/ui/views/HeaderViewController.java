package me.jvegaf.musikbox.ui.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import me.jvegaf.musikbox.tracks.Track;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderViewController implements Initializable {


    @FXML
    private Button openFolderBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        openFolderBtn.setOnMouseClicked(event -> this.mainViewController.openActionListener());
    }

    public void playTrack(Track track) {
        System.out.println("playing"); }

}
