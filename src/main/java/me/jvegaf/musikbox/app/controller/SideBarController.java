package me.jvegaf.musikbox.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.application.create.CreatePlaylistCommand;
import me.jvegaf.musikbox.context.playlists.application.update.UpdatePlaylistCommand;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import me.jvegaf.musikbox.shared.domain.bus.event.DomainEventSubscriber;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;
import me.jvegaf.musikbox.shared.domain.playlist.PlaylistCreatedDomainEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@FxmlView
@DomainEventSubscriber({ PlaylistCreatedDomainEvent.class })
public class SideBarController {

    private final QueryBus   queryBus;
    private final CommandBus commandBus;

    @FXML
    private Button                     addBtn;
    @FXML
    private ListView<PlaylistResponse> libraryListView;
    @FXML
    private ListView<PlaylistResponse> playlistListView;
    private MultipleSelectionModel<PlaylistResponse> selectionModel;


    @Autowired
    public SideBarController(QueryBus queryBus, CommandBus commandBus) {
        this.queryBus   = queryBus;
        this.commandBus = commandBus;
    }

    @EventListener
    public void OnPlaylistCreated(PlaylistCreatedDomainEvent event) {
//        PlaylistResponse response = (PlaylistResponse) queryBus.ask(new FindPlaylistQuery(event.aggregateId()));
//        playlistListView.getItems().add(response);
//        log.info("Playlist created: " + response.name());
    }


    @FXML
    public void initialize() {
//        PlaylistsResponse response = (PlaylistsResponse) queryBus.ask(new SearchAllPlaylistsQuery());
        ObservableList<PlaylistResponse> playlists = FXCollections.observableArrayList();
//        playlists.addAll(response.playlists());

        playlistListView.setItems(playlists);

        //        this.libraryListView.setCellFactory(param -> new PlaylistCell());
        playlistListView.setCellFactory(param -> {
            ListCell<PlaylistResponse> cell = new PlaylistCell();
            cell.setOnMouseClicked(event -> selectionModel.select(cell.getIndex()));
            return cell;
        });
        selectionModel = playlistListView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        playlistListView.setEditable(true);
        playlistListView.setOnEditCommit(t -> {
            playlistListView.getItems().set(t.getIndex(), t.getNewValue());
            commandBus.dispatch(new UpdatePlaylistCommand(t.getNewValue().id(), t.getNewValue().name()));
        });

        addBtn.setOnAction(event -> commandBus.dispatch(new CreatePlaylistCommand("New Playlist")));


    }
}
