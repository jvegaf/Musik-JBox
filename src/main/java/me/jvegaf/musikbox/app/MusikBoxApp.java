package me.jvegaf.musikbox.app;


import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.jvegaf.musikbox.app.controller.MainController;
import me.jvegaf.musikbox.shared.infrastructure.util.ResizeHelper;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


public final class MusikBoxApp extends Application {

    private ConfigurableApplicationContext applicationContext;
    private FxWeaver                       fxWeaver;
    private Scene                          mainScene;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void init() {
        applicationContext =
                new SpringApplicationBuilder().sources(Main.class)
                                              .run(getParameters().getRaw().toArray(new String[ 0 ]));

        fxWeaver = applicationContext.getBean(FxWeaver.class);
    }

    @Override
    public void start(Stage stage) {
        CSSFX.start();
        Parent root = fxWeaver.loadView(MainController.class);
        mainScene = new Scene(root, 1440, 800);
        mainScene.setFill(Color.TRANSPARENT);
        stage.setScene(mainScene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMinHeight(800);
        stage.setMinWidth(1440);
        mainScene.getStylesheets().add("/styles/dark.css");

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
