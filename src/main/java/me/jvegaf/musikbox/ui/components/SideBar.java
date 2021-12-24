package me.jvegaf.musikbox.ui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import me.jvegaf.musikbox.ui.views.MainViewController;
import me.jvegaf.musikbox.tracks.TracksRepository;

import java.io.IOException;
import java.net.URL;

public class SideBar extends VBox {

    @FXML
    private ListView libraryListView;
    @FXML
    private ListView playlistListView;

    private MainViewController mvController;
    private TracksRepository tracksRepository;

    public SideBar() {
        URL resource = getClass().getResource("/components/SideBar.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            System.out.println("error en sidebar");
            e.printStackTrace();
        }
    }


}
