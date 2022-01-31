package com.github.jvegaf.musikbox.app.controller;

import com.github.jvegaf.musikbox.context.tracks.infrastructure.file.CollectFilesCommand;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import com.github.jvegaf.musikbox.shared.domain.bus.event.EventBus;
import com.github.jvegaf.musikbox.shared.domain.library.LibraryInitializedDomainEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import lombok.extern.log4j.Log4j2;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;

@Log4j2
@Component
@FxmlView
public final class OnBoarding {


    private final CommandBus commandBus;
    private final EventBus   eventBus;
    @FXML
    private       AnchorPane folderContainer;

    public OnBoarding(CommandBus commandBus, EventBus eventBus) {
        this.commandBus = commandBus;
        this.eventBus   = eventBus;
    }

    @FXML
    public void initialize() {
        folderContainer.setOnMouseClicked(e -> openActionListener());
    }

    private void openActionListener() {
        log.info("Open folder action");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(folderContainer.getScene()
                                                                         .getWindow());
        if (selectedFolder==null) return;
        commandBus.dispatch(new CollectFilesCommand(selectedFolder));
        eventBus.publish(Collections.singletonList(new LibraryInitializedDomainEvent()));
    }
}
