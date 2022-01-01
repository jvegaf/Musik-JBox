package me.jvegaf.musikbox.app.controller;

import com.google.gson.JsonArray;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.player.PlaybackCommand;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.application.find.FindTrackQuery;
import me.jvegaf.musikbox.context.tracks.application.search_all.SearchAllTracksQuery;
import me.jvegaf.musikbox.context.tracks.infrastructure.file.CollectFilesCommand;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import me.jvegaf.musikbox.shared.domain.bus.event.DomainEventSubscriber;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;
import me.jvegaf.musikbox.shared.domain.track.TrackCreatedDomainEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
@Component
@FxmlView
@DomainEventSubscriber({ TrackCreatedDomainEvent.class })
public class TracklistController {

    private final QueryBus                                         queryBus;
    private final CommandBus                                       commandBus;
    @FXML
    private       TableView<TrackResponse>                         songsTableView;
    @FXML
    private       TableColumn<TrackResponse, String>               titleColumn;
    @FXML
    private       TableColumn<TrackResponse, String>               artistColumn;
    @FXML
    private       TableColumn<TrackResponse, String>               albumColumn;
    @FXML
    private       TableColumn<TrackResponse, String>               genreColumn;
    @FXML
    private       TableColumn<TrackResponse, String>               durationColumn;
    @FXML
    private       TableColumn<TrackResponse, String>               bpmColumn;
    @FXML
    private       TableColumn<TrackResponse, String>               yearColumn;
    @FXML
    private       TableColumn<TrackResponse, String>               keyColumn;
    private       TableView.TableViewSelectionModel<TrackResponse> selectionModel;

    @Autowired
    public TracklistController(QueryBus queryBus, CommandBus commandBus) {
        this.queryBus               = queryBus;
        this.commandBus             = commandBus;
    }

    @EventListener
    public void onTrackCreated(TrackCreatedDomainEvent event) {
        TrackResponse trackResponse = (TrackResponse) queryBus.ask(new FindTrackQuery(event.aggregateId()));
        songsTableView.getItems().add(trackResponse);
        songsTableView.refresh();
        log.info("TrackResponse added: {}", trackResponse.title());
    }


    @FXML
    public void initialize() {
        TracksResponse response = (TracksResponse) queryBus.ask(new SearchAllTracksQuery());
        ObservableList<TrackResponse> list = FXCollections.observableArrayList();
        list.addAll(response.tracks());
        songsTableView.setItems(list);

        selectionModel = this.songsTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().isPresent() ? cellData.getValue().title().get() : ""));
        artistColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().artist().isPresent() ? cellData.getValue().artist().get() : "" ));
        albumColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().album().isPresent() ? cellData.getValue().album().get() : "" ));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().genre().isPresent() ? cellData.getValue().genre().get() : "" ));
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().duration()));
        bpmColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().bpm().isPresent() ?
                                            String.valueOf(cellData.getValue().bpm().get()) : "");
        });
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().year().isPresent() ? cellData.getValue().year().get() : "" ));
        keyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().key().isPresent() ? cellData.getValue().key().get() : "" ));

        songsTableView.setRowFactory(tv -> {
            TableRow<TrackResponse> row = new TableRow<>();
            ContextMenu menu = getContextMenu();
            row.setContextMenu(menu);
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) {
                    return;
                }
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    commandBus.dispatch(new PlaybackCommand(selectionModel.getSelectedItem().id()));
                }
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
        //        detailItem.setOnAction(actionEvent -> this.commandHandler.showTrackDetail(this.selectionModel
        //        .getSelectedItem()));
        detailItem.disableProperty()
                  .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems().size() != 1,
                                                      selectionModel.getSelectedItems()));
        MenuItem playItem = new MenuItem("Play Song");
        playItem.disableProperty()
                .bind(Bindings.createBooleanBinding(() -> this.selectionModel.getSelectedItems().size() != 1,
                                                    selectionModel.getSelectedItems()));
        //        playItem.setOnAction(actionEvent -> this.commandHandler.playTrack(this.selectionModel
        //        .getSelectedItem()));


        menu.getItems().addAll(fixallItem, new SeparatorMenuItem(), detailItem, playItem);
        return menu;
    }

    private void addEventHandler(final Node keyNode) {
        keyNode.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (selectionModel.getSelectedItems().size() == 1) return;
                //                this.commandHandler.playTrack(selectionModel.getSelectedItem());
            }
        });
    }

}
