package me.jvegaf.musikbox.app.controller;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.components.HeaderController;
import me.jvegaf.musikbox.app.components.SideBarController;
import me.jvegaf.musikbox.app.components.TracklistController;
import me.jvegaf.musikbox.context.tracks.infrastructure.file.CollectFilesCommand;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
@Component
@FxmlView("views/MainView.fxml")
public class MainController implements Initializable {

    @FXML
    private final FxControllerAndView<HeaderController, HBox>          header;
    @FXML
    private final FxControllerAndView<SideBarController, VBox>         sidebar;
    @FXML
    private final FxControllerAndView<TracklistController, AnchorPane> tracklist;

    private final CommandBus bus;
    @FXML
    private       HBox       headerPane;
    @FXML
    private       VBox       sidebarPane;
    @FXML
    private       VBox       tracklistPane;

    @Autowired
    public MainController(FxControllerAndView<HeaderController, HBox> header,
                          FxControllerAndView<SideBarController, VBox> sidebar,
                          FxControllerAndView<TracklistController, AnchorPane> tracklist,
                          CommandBus bus) {
        this.header    = header;
        this.sidebar   = sidebar;
        this.tracklist = tracklist;
        this.bus       = bus;
    }

    private void autoLoad() {
        String path = Dotenv.load().get("DEV_MUSIC_PATH");
        log.info("Loading tracks from: {}", path);
        this.bus.dispatch(new CollectFilesCommand(new File(path)));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        header.getView().ifPresent(h -> headerPane.getChildren().add(h));
        sidebar.getView().ifPresent(s -> sidebarPane.getChildren().add(s));
        tracklist.getView().ifPresent(t -> {
            tracklistPane.getChildren().add(t);
            autoLoad();
        });
    }
}

