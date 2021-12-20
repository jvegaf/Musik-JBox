package me.jvegaf.musikbox.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import me.jvegaf.musikbox.controllers.MainViewController;
import me.jvegaf.musikbox.models.Track;
import me.jvegaf.musikbox.services.LibraryService;

import java.io.IOException;
import java.net.URL;

public class Tracklist extends AnchorPane {

    @FXML TableView songsTableView;
    @FXML TableColumn titleColumn;
    @FXML TableColumn artistColumn;
    @FXML TableColumn albumColumn;
    @FXML TableColumn genreColumn;
    @FXML TableColumn bpmColumn;
    @FXML TableColumn yearColumn;

    private MainViewController mvController;
    private LibraryService libraryService;
    private Track selectedTrack;

    public Tracklist() {
        URL resource = getClass().getResource("/components/Tracklist.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void injectDeeps(MainViewController mvController, LibraryService libService) {
        this.mvController = mvController;
        this.libraryService = libService;
        this.songsTableView.setItems(this.libraryService.getTracks());
    }

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        bpmColumn.setCellValueFactory(new PropertyValueFactory<>("bpm"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        songsTableView.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            ContextMenu menu = new ContextMenu();
            MenuItem detailItem = new MenuItem("View Detail");
            detailItem.setOnAction(actionEvent -> this.mvController.onViewDetailActionListener(this.selectedTrack));
            MenuItem playItem = new MenuItem("Play Song");
            playItem.setOnAction(actionEvent -> this.mvController.playTrackAction(this.selectedTrack));
            menu.getItems().addAll(detailItem, playItem);
            row.setContextMenu(menu);
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) { return; }
                this.selectedTrack = row.getItem();
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    mvController.playTrackAction(this.selectedTrack);
                }
            });
            return row;
        });
    }

}
