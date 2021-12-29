package me.jvegaf.musikbox.ui.components;

import com.google.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import me.jvegaf.musikbox.bus.CommandBus;
import me.jvegaf.musikbox.services.parser.TimeParser;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Tracklist extends AnchorPane implements Initializable {

    private final TracksRepository tracksRepository;
    private final CommandBus commandHandler;
    private final Logger logger = Logger.getLogger(Tracklist.class);
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
    private TableView.TableViewSelectionModel<Track> selectionModel;

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

        this.tracksRepository.tracksObjectProperty()
                             .addListener((observable, oldValue, newValue) -> songsTableView.setItems(newValue));

        selectionModel = this.songsTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
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
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    this.commandHandler.playTrack(this.selectionModel.getSelectedItem());
                }
            });
            return row;
        });
    }

    private ContextMenu getContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem fixallItem = new MenuItem();
        selectionModel.getSelectedItems().addListener((ListChangeListener<Track>) c -> {
            if (selectionModel.getSelectedItems().size() > 1) {
                fixallItem.setText("Fix " + selectionModel.getSelectedItems().size() + " tracks");
            }
            if (selectionModel.getSelectedItems().size() == 1) {
                fixallItem.setText("Fix Track");
            }
        });
        fixallItem.setOnAction(actionEvent -> {
            this.commandHandler.fixTags(selectionModel.getSelectedItems());
            selectionModel.clearSelection();
        });
        MenuItem detailItem = new MenuItem("View Detail");
        detailItem.setOnAction(actionEvent -> this.commandHandler.showTrackDetail(this.selectionModel.getSelectedItem()));
        detailItem.disableProperty()
                  .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems().size() != 1,
                                                      selectionModel.getSelectedItems()));
        MenuItem playItem = new MenuItem("Play Song");
        playItem.disableProperty()
                .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems().size() != 1,
                                                    selectionModel.getSelectedItems()));
        playItem.setOnAction(actionEvent -> this.commandHandler.playTrack(this.selectionModel.getSelectedItem()));


        menu.getItems().addAll(
                fixallItem,
                new SeparatorMenuItem(),
                detailItem,
                playItem
        );
        return menu;
    }

    private void addEventHandler(final Node keyNode) {
        keyNode.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (selectionModel.getSelectedItems().size() == 1) return;
                this.commandHandler.playTrack(selectionModel.getSelectedItem());
            }
        });
    }

}
