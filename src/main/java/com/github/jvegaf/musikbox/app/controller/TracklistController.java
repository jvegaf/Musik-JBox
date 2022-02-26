package com.github.jvegaf.musikbox.app.controller;

import com.github.jvegaf.musikbox.app.collection.MusicCollection;
import com.github.jvegaf.musikbox.app.player.MusicPlayer;
import com.github.jvegaf.musikbox.context.shared.application.FixTagsCommand;
import com.github.jvegaf.musikbox.shared.domain.TrackResponse;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import lombok.extern.log4j.Log4j2;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.jvegaf.musikbox.app.items.Category.PLAYLIST;

@Log4j2
@Component
@FxmlView
public class TracklistController {

    public static final DataFormat                                       SERIALIZED_MIME_TYPE = new DataFormat(
            "application/x-java-serialized-object");
    private final       MusicPlayer                                      musicPlayer;
    private final       FxWeaver                                         fxWeaver;
    private final       MusicCollection                                  collection;
    private final       CommandBus                                       bus;
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

    private String playingId;

    @Autowired
    public TracklistController(
            MusicPlayer musicPlayer, FxWeaver fxWeaver, MusicCollection collection, CommandBus bus
    ) {
        this.musicPlayer = musicPlayer;
        this.fxWeaver    = fxWeaver;
        this.collection  = collection;
        this.bus         = bus;
    }

    @FXML
    public void initialize() {
        songsTableView.setItems(collection.tracksProperty());

        collection.tracksProperty()
                  .addListener((ListChangeListener<? super TrackResponse>) c -> songsTableView.refresh());


        selectionModel = this.songsTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //TODO: Fixme

//        positionColumn.setCellValueFactory(cellData -> {
//            if (cellData.getValue() instanceof TrackPlaylistResponse r) {
//
//                return new SimpleStringProperty(r.positionStr());
//            }
//            return new SimpleStringProperty("");
//        });

        positionColumn.setVisible(collection.getCategory()==PLAYLIST);

        collection.collectionCategoryProperty()
                  .addListener((observable, oldValue, newValue) -> positionColumn.setVisible(newValue.equals(PLAYLIST)));

        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                     .title()));
        artistColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                      .artist()
                                                                                      .isPresent()
                                                                              ? cellData.getValue()
                                                                                        .artist()
                                                                                        .get()
                                                                              :""));
        albumColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                     .album()
                                                                                     .isPresent() ? cellData.getValue()
                                                                                                            .album()
                                                                                                            .get():""));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                     .genre()
                                                                                     .isPresent() ? cellData.getValue()
                                                                                                            .genre()
                                                                                                            .get():""));
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                        .duration()));
        bpmColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                   .bpm()
                                                                                   .isPresent() ? String.valueOf(
                cellData.getValue()
                        .bpm()
                        .get()):""));
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                    .year()
                                                                                    .isPresent() ? cellData.getValue()
                                                                                                           .year()
                                                                                                           .get():""));
        keyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                                                                                   .key()
                                                                                   .isPresent() ? cellData.getValue()
                                                                                                          .key()
                                                                                                          .get():""));

        songsTableView.setRowFactory(tv -> {
            TableRow<TrackResponse> row = new TableRow<>() {
                @Override
                protected void updateItem(TrackResponse item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item==null) return;
                    if (Objects.equals(item.id(), playingId)) {
                        setStyle("-fx-background-color: -selected-focus-lost;");
                    }
                    else {
                        setStyle("");
                    }
                }
            };

            ContextMenu menu = getContextMenu();

            row.setContextMenu(menu);
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) {
                    return;
                }
                if (event.getButton()==MouseButton.PRIMARY && event.getClickCount()==2) {
                    musicPlayer.playTrack(selectionModel.getSelectedItem());
                    playingId =
                            selectionModel.getSelectedItem()
                                          .id();
                    songsTableView.getSelectionModel()
                                  .clearSelection();
                    songsTableView.refresh();
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
                log.info("Dragging: " +
                         db.getContent(SERIALIZED_MIME_TYPE)
                           .toString());
                ev.consume();
            });
            return row;
        });
    }

    private ContextMenu getContextMenu() {
        ContextMenu menu       = new ContextMenu();
        MenuItem    fixallItem = new MenuItem();
        selectionModel.getSelectedItems()
                      .addListener((ListChangeListener<TrackResponse>) c -> {
                          if (selectionModel.getSelectedItems()
                                            .size() > 1) {
                              fixallItem.setText("Fix " +
                                                 selectionModel.getSelectedItems()
                                                               .size() +
                                                 " tracks");
                          }
                          if (selectionModel.getSelectedItems()
                                            .size()==1) {
                              fixallItem.setText("Fix Track");
                          }
                      });
        fixallItem.setOnAction(actionEvent -> {
            List<TrackResponse> list = new ArrayList<>(selectionModel.getSelectedItems());
            fixAllTags(list);
            //            selectionModel.clearSelection();
        });
        MenuItem detailItem = new MenuItem("View Detail");
        detailItem.disableProperty()
                  .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems()
                                                                               .size()!=1,
                                                      selectionModel.getSelectedItems()));
        detailItem.setOnAction(actionEvent -> {
            DetailController detailController = fxWeaver.loadController(DetailController.class);
            detailController.setDetails(selectionModel.getSelectedItem(),
                                        songsTableView.getScene()
                                                      .getWindow());
            detailController.show();
        });
        MenuItem playItem = new MenuItem("Play Song");
        playItem.disableProperty()
                .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems()
                                                                             .size()!=1,
                                                    selectionModel.getSelectedItems()));

        playItem.setOnAction(actionEvent -> musicPlayer.playTrack(selectionModel.getSelectedItem()));

        menu.getItems()
            .addAll(detailItem, new SeparatorMenuItem(), playItem, new SeparatorMenuItem(), fixallItem);
        return menu;
    }

    private void fixAllTags(List<TrackResponse> selectedItems) {

        new Thread(() -> selectedItems.forEach(trackResponse -> bus.dispatch(new FixTagsCommand(trackResponse)))).start();
        selectionModel.clearSelection();
    }

    private void addEventHandler(final Node keyNode) {
        keyNode.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode()==KeyCode.SPACE) {
                if (selectionModel.getSelectedItems()
                                  .size()==1) return;
                musicPlayer.playTrack(selectionModel.getSelectedItem());
            }
        });
    }
}
