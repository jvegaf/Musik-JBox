package me.jvegaf.musikbox.ui.components;

import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import me.jvegaf.musikbox.services.player.MusicPlayer;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Tracklist extends AnchorPane implements Initializable {

    @FXML
    private TableView<Track> songsTableView;
    @FXML
    private TableColumn<Track, String> titleColumn;
    @FXML
    private TableColumn<Track, String> artistColumn;
    @FXML
    private TableColumn<Track, String> albumColumn;
    @FXML
    private TableColumn<Track, String> genreColumn;
    @FXML
    private TableColumn<Track, String> durationColumn;
    @FXML
    private TableColumn<Track, String> bpmColumn;
    @FXML
    private TableColumn<Track, String> yearColumn;

    private Track selectedTrack;

    private final TracksRepository tracksRepository;
    private final MusicPlayer player;

    @Inject
    public Tracklist(TracksRepository repository, MusicPlayer player) {
        this.tracksRepository = repository;
        this.player = player;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.songsTableView.setItems(this.tracksRepository.tracksObjectProperty().getValue());
        this.tracksRepository.tracksObjectProperty().addListener((observable, oldValue, newValue) -> refreshData(newValue));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        bpmColumn.setCellValueFactory(new PropertyValueFactory<>("bpm"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        songsTableView.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            ContextMenu menu = new ContextMenu();
            MenuItem detailItem = new MenuItem("View Detail");
//            detailItem.setOnAction(actionEvent -> this.mvController.detailActionListener(this.selectedTrack));
            MenuItem playItem = new MenuItem("Play Song");
            playItem.setOnAction(actionEvent -> this.player.playTrack(this.selectedTrack));
            menu.getItems().addAll(detailItem, playItem);
            row.setContextMenu(menu);
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) { return; }
                this.selectedTrack = row.getItem();
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    this.player.playTrack(this.selectedTrack);
                }
            });
            return row;
        });
    }

    private void refreshData(ObservableList<Track> newValue) {
        songsTableView.setItems(newValue);
//        songsTableView.refresh();
    }


}
