package me.jvegaf.musikbox.app;


import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.goxr3plus.fxborderlessscene.borderless.CustomStage;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.jvegaf.musikbox.app.controller.MainController;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


public final class MusikBoxApp extends Application {

    public static final int                            WIDTH  = 1440;
    public static final int                            HEIGHT = 800;
    static              Stage                          primaryStage;
    private             ConfigurableApplicationContext applicationContext;
    private             FxWeaver                       fxWeaver;

    @Override
    public void init() {
        applicationContext =
                new SpringApplicationBuilder().sources(Main.class)
                                              .run(getParameters().getRaw().toArray(new String[ 0 ]));

        fxWeaver = applicationContext.getBean(FxWeaver.class);
    }

    @Override
    public void start(Stage primaryStage) {
        CSSFX.start();
        CustomStage stage = new CustomStage(StageStyle.TRANSPARENT);
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);

        MusikBoxApp.primaryStage = stage;

        Parent root = fxWeaver.loadView(MainController.class);
        BorderlessScene borderlessScene = stage.craftBorderlessScene(root);
        borderlessScene.setFill(Color.TRANSPARENT);

        borderlessScene.getStylesheets().add("/styles/dark.css");

        borderlessScene.setMoveControl(root);

        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setScene(borderlessScene);

        stage.showAndAdjust();

    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
