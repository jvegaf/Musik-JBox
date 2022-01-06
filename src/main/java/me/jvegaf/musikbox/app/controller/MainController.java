package me.jvegaf.musikbox.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.collection.Collection;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@FxmlView
public class MainController {

    private final Collection                                           collection;
    private final CommandBus                                           bus;
    @FXML
    private final FxControllerAndView<HeaderController, HBox>          header;
    @FXML
    private final FxControllerAndView<SideBarController, VBox>         sidebar;
    @FXML
    private final FxControllerAndView<TracklistController, AnchorPane> tracklist;
    @FXML
    private       VBox                                                 container;
    @FXML
    private       Label                                                playlistNameLabel;
    @FXML
    private       Label                                                playlistDetailsLabel;

    public Label leftStatusLabel;


    @Autowired
    public MainController(Collection collection,
                          CommandBus bus,
                          FxControllerAndView<HeaderController, HBox> header,
                          FxControllerAndView<SideBarController, VBox> sidebar,
                          FxControllerAndView<TracklistController, AnchorPane> tracklist) {
        this.collection = collection;
        this.bus        = bus;
        this.header     = header;
        this.sidebar    = sidebar;
        this.tracklist  = tracklist;
    }

    @FXML
    public void initialize() {
        container.getChildren().get(0).setManaged(false);
        collection.collectionCategoryProperty().addListener((observable, oldValue, newValue) -> {
            container.getChildren().get(0).setManaged(shouldShow(newValue));
        });
        playlistNameLabel.textProperty().bind(collection.playListNameProperty());
        collection.collectionTracksCountProperty().addListener((observable, oldValue, newValue) -> {
            playlistDetailsLabel.setText("Total " + newValue.toString() + " tracks");
        });
    }

    private boolean shouldShow(Category cat) {
        return cat == Category.PLAYLIST;
    }
}

