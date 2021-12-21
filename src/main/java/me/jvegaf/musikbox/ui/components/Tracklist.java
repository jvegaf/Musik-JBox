package me.jvegaf.musikbox.ui.components;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import me.jvegaf.musikbox.ui.views.MainViewController;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TrackListRepository;

import java.io.IOException;
import java.net.URL;

public class Tracklist extends AnchorPane {

    @FXML
    private TableView songsTableView;
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn artistColumn;
    @FXML
    private TableColumn albumColumn;
    @FXML
    private TableColumn genreColumn;
    @FXML
    private TableColumn bpmColumn;
    @FXML
    private TableColumn yearColumn;

    @Inject
    private MainViewController mvController;
    private TrackListRepository tracksRepository;
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

    public void initialize() {
        this.songsTableView.setItems(this.tracksRepository.getAll());

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
