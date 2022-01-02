package me.jvegaf.musikbox.app.controller;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.playlists.application.create.CreatePlaylistCommand;
import me.jvegaf.musikbox.context.tracks.infrastructure.file.CollectFilesCommand;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Log4j2
@Component
@FxmlView
public class MainController {


    private final CommandBus                                           bus;
    @FXML
    private final FxControllerAndView<HeaderController, HBox>          header;
    @FXML
    private final FxControllerAndView<SideBarController, VBox>         sidebar;
    @FXML
    private final FxControllerAndView<TracklistController, AnchorPane> tracklist;
    public Label leftStatusLabel;


    @Autowired
    public MainController(CommandBus bus,
                          FxControllerAndView<HeaderController, HBox> header,
                          FxControllerAndView<SideBarController, VBox> sidebar,
                          FxControllerAndView<TracklistController, AnchorPane> tracklist) {
        this.bus       = bus;
        this.header    = header;
        this.sidebar   = sidebar;
        this.tracklist = tracklist;
    }

    private void autoLoad() {
        String path = Dotenv.load().get("DEV_MUSIC_PATH");
        log.info("Loading tracks from: {}", path);
        this.bus.dispatch(new CollectFilesCommand(new File(path)));

        this.bus.dispatch(new CreatePlaylistCommand("House"));
        this.bus.dispatch(new CreatePlaylistCommand("Tech House"));
        this.bus.dispatch(new CreatePlaylistCommand("Deep"));
        this.bus.dispatch(new CreatePlaylistCommand("ElectroRodeo"));
    }

    @FXML
    public void initialize() {
        autoLoad();
    }

}

