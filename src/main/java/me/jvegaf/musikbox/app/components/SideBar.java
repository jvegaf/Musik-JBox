package me.jvegaf.musikbox.app.components;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import me.jvegaf.musikbox.bus.CommandBus;
import me.jvegaf.musikbox.context.playlists.Playlist;
import me.jvegaf.musikbox.context.playlists.PlaylistRepository;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBar extends VBox implements Initializable {

    @FXML
    private ListView<Playlist> libraryListView;
    @FXML
    private ListView<Playlist> playlistListView;

    @SuppressWarnings("FieldCanBeLocal")
    private final CommandBus commandHandler;
    private final PlaylistRepository repository;
    private final ObservableList<Playlist> libraryObservableList;

    private final Logger LOG = Logger.getLogger(SideBar.class);

    @Inject
    public SideBar(CommandBus commandHandler, PlaylistRepository repository) {
        this.commandHandler = commandHandler;
        this.repository = repository;
        this.libraryObservableList = FXCollections.observableArrayList();
        URL resource = getClass().getResource("/components/SideBar.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            LOG.error("error en sidebar");
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.libraryObservableList.add(Playlist.createPlaylist("mainLibrary"));
        this.libraryListView.setItems(this.libraryObservableList);
        this.playlistListView.setItems(this.repository.PlaylistsProperty().getValue());

        this.repository
                .PlaylistsProperty()
                .addListener((observable, oldValue, newValue) -> this.playlistListView.setItems(newValue));

        this.libraryListView.setCellFactory(param -> new PlaylistCell());
        this.playlistListView.setCellFactory(param -> new PlaylistCell());

        autoloadPlaylists();
    }

    private void autoloadPlaylists() {
        this.repository.addPlaylist(Playlist.createPlaylist("Playlist 1"));
        this.repository.addPlaylist(Playlist.createPlaylist("Playlist 2"));
        this.repository.addPlaylist(Playlist.createPlaylist("Playlist 3"));
        this.repository.addPlaylist(Playlist.createPlaylist("Playlist 4"));
    }
}
