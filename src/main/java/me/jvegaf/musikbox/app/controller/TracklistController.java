package me.jvegaf.musikbox.app.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.collection.MusicCollection;
import me.jvegaf.musikbox.app.player.MusicPlayer;
import me.jvegaf.musikbox.context.trackplaylist.application.TrackPlaylistResponse;
import me.jvegaf.musikbox.shared.domain.TrackResponse;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Log4j2
@Component
@FxmlView
public class TracklistController {

    public static final DataFormat
                                                                         SERIALIZED_MIME_TYPE =
            new DataFormat("application/x-java-serialized-object");
    private final       MusicPlayer                                      musicPlayer;
    private final       FxWeaver                                         fxWeaver;
    private final       MusicCollection                                  collection;
    @FXML
    private             TableView<TrackResponse>                         songsTableView;
    @FXML
    private             TableColumn<TrackResponse, String>               positionColumn;
    @FXML
    private             TableColumn<TrackResponse, String>               titleColumn;
    @FXML
    private             TableColumn<TrackResponse, String>               artistColumn;
    @FXML
    private             TableColumn<TrackResponse, String>               albumColumn;
    @FXML
    private             TableColumn<TrackResponse, String>               genreColumn;
    @FXML
    private             TableColumn<TrackResponse, String>               durationColumn;
    @FXML
    private             TableColumn<TrackResponse, String>               bpmColumn;
    @FXML
    private             TableColumn<TrackResponse, String>               yearColumn;
    private             TableView.TableViewSelectionModel<TrackResponse> selectionModel;
    @FXML
    private             TableColumn<TrackResponse, String>               keyColumn;

    @Autowired
    public TracklistController(MusicPlayer musicPlayer, FxWeaver fxWeaver, MusicCollection collection) {
        this.musicPlayer = musicPlayer;
        this.fxWeaver    = fxWeaver;
        this.collection  = collection;
    }

    @FXML
    public void initialize() {
        ObservableList<TrackResponse> list = FXCollections.observableArrayList();
        list.addAll(collection.getTracks());
        songsTableView.setItems(list);

        collection.tracksProperty().addListener((observable, oldValue, newValue) -> {
            songsTableView.getItems().clear();
            songsTableView.getItems().addAll(collection.getTracks());
            songsTableView.refresh();
        });


        selectionModel = this.songsTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        positionColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof TrackPlaylistResponse r) {

                return new SimpleStringProperty(r.positionStr());
            }
            return new SimpleStringProperty("");
        });
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title()));
        artistColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().artist().isPresent() ?
                                                                              cellData.getValue().artist().get() :
                                                                              ""));
        albumColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().album().isPresent() ?
                                                                             cellData.getValue().album().get() :
                                                                             ""));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().genre().isPresent() ?
                                                                             cellData.getValue().genre().get() :
                                                                             ""));
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().duration()));
        bpmColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().bpm().isPresent() ?
                                                                           String.valueOf(cellData.getValue()
                                                                                                  .bpm()
                                                                                                  .get()) :
                                                                           ""));
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().year().isPresent() ?
                                                                            cellData.getValue().year().get() :
                                                                            ""));
        keyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().key().isPresent() ?
                                                                           cellData.getValue().key().get() :
                                                                           ""));

        songsTableView.setRowFactory(tv -> {
            TableRow<TrackResponse> row = new TableRow<>();
            ContextMenu menu = getContextMenu();
            row.setContextMenu(menu);
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) {
                    return;
                }
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    musicPlayer.playTrack(selectionModel.getSelectedItem());
                    songsTableView.getSelectionModel().clearSelection();
                }
            });
            row.setOnDragDetected(ev -> {
                Dragboard db = row.startDragAndDrop(TransferMode.COPY);

                ArrayList<String>
                        tracksIds =
                        (ArrayList<String>) songsTableView.getSelectionModel()
                                                          .getSelectedItems()
                                                          .stream()
                                                          .map(TrackResponse::id)
                                                          .collect(Collectors.toList());

                ClipboardContent content = new ClipboardContent();
                content.put(SERIALIZED_MIME_TYPE, tracksIds);
                db.setContent(content);
                log.info("Dragging: " + db.getContent(SERIALIZED_MIME_TYPE).toString());
                ev.consume();
            });
            return row;
        });
    }

    private ContextMenu getContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem fixallItem = new MenuItem();
        selectionModel.getSelectedItems().addListener((ListChangeListener<TrackResponse>) c -> {
            if (selectionModel.getSelectedItems().size() > 1) {
                fixallItem.setText("Fix " + selectionModel.getSelectedItems().size() + " tracks");
            }
            if (selectionModel.getSelectedItems().size() == 1) {
                fixallItem.setText("Fix Track");
            }
        });
        fixallItem.setOnAction(actionEvent -> {
            //            this.commandHandler.fixTags(selectionModel.getSelectedItems());
            selectionModel.clearSelection();
        });
        MenuItem detailItem = new MenuItem("View Detail");
        detailItem.disableProperty()
                  .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems().size() != 1,
                                                      selectionModel.getSelectedItems()));
        detailItem.setOnAction(actionEvent -> {
            DetailController detailController = fxWeaver.loadController(DetailController.class);
            detailController.setDetails(selectionModel.getSelectedItem(), songsTableView.getScene().getWindow());
            detailController.show();
        });
        MenuItem playItem = new MenuItem("Play Song");
        playItem.disableProperty()
                .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems().size() != 1,
                                                    selectionModel.getSelectedItems()));

        playItem.setOnAction(actionEvent -> musicPlayer.playTrack(selectionModel.getSelectedItem()));

        menu.getItems().addAll(fixallItem, new SeparatorMenuItem(), detailItem, playItem);
        return menu;
    }

    private void addEventHandler(final Node keyNode) {
        keyNode.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (selectionModel.getSelectedItems().size() == 1) return;
                musicPlayer.playTrack(selectionModel.getSelectedItem());
            }
        });
    }
}
