package me.jvegaf.musikbox.app.controller;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.jvegaf.musikbox.app.components.Header;
import me.jvegaf.musikbox.app.components.SideBar;
import me.jvegaf.musikbox.app.components.Tracklist;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.services.reporter.Reporter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, MainController {

    @FXML
    private VBox headerPane;
    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane tracklistPane;
    @FXML
    private Label leftStatusLabel;


    private final DetailViewController detailViewController;
    private final Header headerComponent;
    private final SideBar sideBarComponent;
    private final Tracklist tracklistComponent;
    private final Reporter reporter;

    @Inject
    public MainViewController(DetailViewController detailViewController,
                              Header header,
                              SideBar sideBar,
                              Tracklist tracklist,
                              Reporter reporter) {

        this.detailViewController = detailViewController;
        this.headerComponent = header;
        this.sideBarComponent = sideBar;
        this.tracklistComponent = tracklist;
        this.reporter = reporter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerPane.setFillWidth(true);
        headerComponent.prefHeightProperty().bind(headerPane.heightProperty());
        headerComponent.prefWidthProperty().bind(headerPane.widthProperty());
        headerPane.getChildren().add(headerComponent);
        sideBarComponent.prefHeightProperty().bind(sidePane.heightProperty());
        sideBarComponent.prefWidthProperty().bind(sidePane.widthProperty());
        sidePane.getChildren().add(sideBarComponent);
        tracklistComponent.prefHeightProperty().bind(tracklistPane.heightProperty());
        tracklistComponent.prefWidthProperty().bind(tracklistPane.widthProperty());
        tracklistPane.getChildren().add(tracklistComponent);
        leftStatusLabel.textProperty().bind(reporter.StatusProperty());
    }


    @Override
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
        Scene detailScene = new Scene(root, 600, 800);
        detailScene.getStylesheets().add("/styles/dark.css");
        detailViewController.setTrack(t);
        detailStage.setTitle("Song Detail");
        detailStage.setResizable(false);
        detailStage.setScene(detailScene);
        detailStage.initOwner(headerPane.getScene().getWindow());
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.show();
    }

}
