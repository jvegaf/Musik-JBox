package com.github.jvegaf.musikbox.app;


import com.github.jvegaf.musikbox.app.controller.MainController;
import com.github.jvegaf.musikbox.shared.infrastructure.util.ConfigHelper;
import com.github.jvegaf.musikbox.shared.infrastructure.util.ResizeHelper;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

<<<<<<< HEAD
=======
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
>>>>>>> 2c6dddb... init db on app context init

public final class MusikBoxApp extends Application {

    private ConfigurableApplicationContext applicationContext;
    private FxWeaver                       fxWeaver;
    private Scene                          mainScene;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void init() {
<<<<<<< HEAD
=======

>>>>>>> 2c6dddb... init db on app context init
        applicationContext =
                new SpringApplicationBuilder().sources(Main.class)
                                              .initializers(new AppContextInitializer())
                                              .run(getParameters().getRaw()
                                                                  .toArray(new String[0]));

        fxWeaver = applicationContext.getBean(FxWeaver.class);
    }

    @Override
    public void start(Stage stage) {
        Parent root = fxWeaver.loadView(MainController.class);
        mainScene = new Scene(root, 1440, 800);
        mainScene.getStylesheets()
                 .add("styles/dark.css");
        mainScene.setFill(Color.TRANSPARENT);
        stage.setScene(mainScene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMinHeight(800);
        stage.setMinWidth(1440);

        //grab your root here
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        ResizeHelper.addResizeListener(stage);

        stage.show();


    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
