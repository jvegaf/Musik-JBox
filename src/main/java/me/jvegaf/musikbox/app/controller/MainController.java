package me.jvegaf.musikbox.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.collection.MusicCollection;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import me.jvegaf.musikbox.shared.domain.bus.event.DomainEventSubscriber;
import me.jvegaf.musikbox.shared.domain.library.LibraryInitializedDomainEvent;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@FxmlView
@DomainEventSubscriber({LibraryInitializedDomainEvent.class})
public class MainController {

    private final MusicCollection                                      collection;
    private final FxWeaver                                             fxWeaver;
    private final CommandBus                                           bus;
    @FXML
    private final FxControllerAndView<HeaderController, HBox>          header;
    @FXML
    private final FxControllerAndView<SideBarController, VBox>         sidebar;
    @FXML
    private final FxControllerAndView<TracklistController, AnchorPane> tracklist;
    @FXML
    private final FxControllerAndView<OnBoarding, HBox>                onBoarding;

    private final FXMLLoader fxmlLoader;

    private final AnchorPane     playlistDetail;
    private final PlaylistDetail playlistDetailController;
    @FXML
    private       VBox           container;

    @Autowired
    public MainController(
            MusicCollection collection,
            FxWeaver fxWeaver,
            CommandBus bus,
            FxControllerAndView<HeaderController, HBox> header,
            FxControllerAndView<SideBarController, VBox> sidebar,
            FxControllerAndView<TracklistController, AnchorPane> tracklist,
            FxControllerAndView<OnBoarding, HBox> onBoarding
    ) throws IOException {
        this.collection               = collection;
        this.fxWeaver                 = fxWeaver;
        this.bus                      = bus;
        this.header                   = header;
        this.sidebar                  = sidebar;
        this.tracklist                = tracklist;
        this.onBoarding               = onBoarding;
        this.fxmlLoader               = new FXMLLoader(getClass().getResource("PlaylistDetail.fxml"));
        this.playlistDetail           = fxmlLoader.load();
        this.playlistDetailController = fxmlLoader.getController();
        this.playlistDetailController.setCollection(this.collection);
        this.playlistDetailController.initBindings();
    }

    @EventListener
    public void OnLibraryInitialized(LibraryInitializedDomainEvent event) {
        container.getChildren()
                 .remove(0);
        var tracklistView = tracklist.getView();
        tracklistView.ifPresent(anchorPane -> {
            container.getChildren()
                     .add(0, anchorPane);
            VBox.setVgrow(anchorPane, Priority.ALWAYS);
        });
        setPlaylistDetail();
        log.info("Library Initialized");
    }

    private void setPlaylistDetail() {
        collection.collectionCategoryProperty()
                  .addListener((observable, oldValue, newValue) -> {
                      if (newValue==Category.PLAYLIST &&
                          container.getChildren()
                                   .size() < 2) container.getChildren()
                                                         .add(0, playlistDetail);
                      if (newValue==Category.HEAD &&
                          container.getChildren()
                                   .size() > 1) container.getChildren()
                                                         .remove(0);
                  });
    }

    @FXML
    public void initialize() {

        if (collection.getCategory()==Category.HEAD && collection.getSize() > 0) {
            var tracklistView = tracklist.getView();
            tracklistView.ifPresent(anchorPane -> {
                container.getChildren()
                         .add(0, anchorPane);
                VBox.setVgrow(anchorPane, Priority.ALWAYS);
            });
            setPlaylistDetail();
            return;
        }

        var onBoardPane = onBoarding.getView();
        onBoardPane.ifPresent(anchorPane -> container.getChildren()
                                                     .add(0, anchorPane));


    }
}

