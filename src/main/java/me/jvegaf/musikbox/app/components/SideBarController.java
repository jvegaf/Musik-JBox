package me.jvegaf.musikbox.app.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.playlists.domain.Playlist;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
@Component
@FxmlView()
public class SideBarController {

    @FXML
    private ListView<Playlist> libraryListView;

    @FXML
    private ListView<Playlist> playlistListView;


    private final PlaylistRepository       repository;


    @Autowired
    public SideBarController(PlaylistRepository repository) {
        this.repository = repository;
    }


    @FXML
    public void initialize() {

        ObservableList<Playlist> playlists = FXCollections.observableArrayList(repository.searchAll());

        playlistListView.setItems(playlists);

        this.libraryListView.setCellFactory(param -> new PlaylistCell());
        this.playlistListView.setCellFactory(param -> new PlaylistCell());

    }
}
