package me.jvegaf.musikbox.ui.components;

import com.google.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import me.jvegaf.musikbox.bus.CommandBus;
import me.jvegaf.musikbox.services.parser.TimeParser;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private final CommandBus commandHandler;

    private final Logger logger = Logger.getLogger(Tracklist.class);
    private ObservableList<Track> seletedTracks;

    @Inject
    public Tracklist(TracksRepository repository, CommandBus commandHandler) {
        this.tracksRepository = repository;
        this.commandHandler = commandHandler;
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
        this.songsTableView.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            logger.info("Width changed from " + oldWidth + " to " + newWidth);
            titleColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            artistColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            albumColumn.setPrefWidth(newWidth.doubleValue() * 0.19);
            genreColumn.setPrefWidth(newWidth.doubleValue() * 0.15);
            durationColumn.setPrefWidth(newWidth.doubleValue() * 0.05);
            bpmColumn.setPrefWidth(newWidth.doubleValue() * 0.05);
            yearColumn.setPrefWidth(newWidth.doubleValue() * 0.05);
        });
        this.tracksRepository.tracksObjectProperty()
                             .addListener((observable, oldValue, newValue) -> songsTableView.setItems(newValue));

        TableView.TableViewSelectionModel<Track> selectionModel = this.songsTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        seletedTracks = selectionModel.getSelectedItems();
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(
                cellData -> {
                    String durationStr = TimeParser.formatTime(cellData.getValue().getDuration());
                    return new SimpleStringProperty(durationStr);
                });
        bpmColumn.setCellValueFactory(new PropertyValueFactory<>("bpm"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        songsTableView.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            ContextMenu menu = getContextMenu();
            row.setContextMenu(menu);
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) {
                    return;
                }
                this.selectedTrack = row.getItem();
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    this.commandHandler.playTrack(this.selectedTrack);
                }
            });
            return row;
        });
    }

    private ContextMenu getContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem fixallItem = new MenuItem();
        fixallItem.textProperty().bind(Bindings.createStringBinding(() -> "Fix " + seletedTracks.size() + " tracks", seletedTracks));
        fixallItem.setOnAction(actionEvent -> this.commandHandler.fixTags(this.seletedTracks));
        MenuItem detailItem = new MenuItem("View Detail");
        detailItem.setOnAction(actionEvent -> this.commandHandler.showTrackDetail(this.selectedTrack));
        detailItem.disableProperty().bind(Bindings.createBooleanBinding(() -> this.seletedTracks.size() != 1, seletedTracks));
        MenuItem playItem = new MenuItem("Play Song");
        playItem.disableProperty().bind(Bindings.createBooleanBinding(() -> seletedTracks.size() != 1, seletedTracks));
        playItem.setOnAction(actionEvent -> this.commandHandler.playTrack(this.selectedTrack));


        menu.getItems().addAll(
                fixallItem,
                new SeparatorMenuItem(),
                detailItem,
                playItem
        );
        return menu;
    }


}
