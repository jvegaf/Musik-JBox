package me.jvegaf.musikbox.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.collection.MusicCollection;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@FxmlView
public class MainController {

    private final MusicCollection                                      collection;
    private final CommandBus                                           bus;
    @FXML
    private final FxControllerAndView<HeaderController, HBox>          header;
    @FXML
    private final FxControllerAndView<SideBarController, VBox>         sidebar;
    @FXML
    private final FxControllerAndView<TracklistController, AnchorPane> tracklist;
    private final FXMLLoader                                           fxmlLoader;

    private final AnchorPane     playlistDetail;
    private final PlaylistDetail playlistDetailController;
    @FXML
    private       VBox           container;


    @Autowired
    public MainController(MusicCollection collection,
                          CommandBus bus,
                          FxControllerAndView<HeaderController, HBox> header,
                          FxControllerAndView<SideBarController, VBox> sidebar,
                          FxControllerAndView<TracklistController, AnchorPane> tracklist) throws IOException {
        this.collection               = collection;
        this.bus                      = bus;
        this.header                   = header;
        this.sidebar                  = sidebar;
        this.tracklist                = tracklist;
        this.fxmlLoader               = new FXMLLoader(getClass().getResource("PlaylistDetail.fxml"));
        this.playlistDetail           = fxmlLoader.load();
        this.playlistDetailController = fxmlLoader.getController();
        this.playlistDetailController.setCollection(this.collection);
        this.playlistDetailController.initBindings();
    }

    @FXML
    public void initialize() {
        log.info("container size: " + container.getChildren().size());

        collection.collectionCategoryProperty().addListener((observable, oldValue, newValue) -> {
            log.info("container size: " + container.getChildren().size());
            log.info("new value " + newValue.toString());
            if (newValue == Category.PLAYLIST && container.getChildren().size() < 2) container.getChildren()
                                                                                              .add(0, playlistDetail);
            if (newValue == Category.HEAD && container.getChildren().size() > 1) container.getChildren().remove(0);
        });
    }
}

