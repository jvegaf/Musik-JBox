package me.jvegaf.musikbox.app.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.jvegaf.musikbox.app.components.HeaderController;
import me.jvegaf.musikbox.app.components.SideBarController;
import me.jvegaf.musikbox.app.components.TracklistController;
import me.jvegaf.musikbox.context.tracks.infrastructure.file.CollectFilesCommand;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/MainView.fxml")
public class MainController {

    private final FxControllerAndView<HeaderController, HBox>          header;
    private final FxControllerAndView<SideBarController, VBox>         sidebar;
    private final FxControllerAndView<TracklistController, AnchorPane> tracklist;
    private final CommandBus                                           bus;

    @FXML
    private Label leftStatusLabel;

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

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        leftStatusLabel.setText("Musikbox Ready");
    }

}

