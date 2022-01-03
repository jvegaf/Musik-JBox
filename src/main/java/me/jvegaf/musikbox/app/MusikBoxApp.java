package me.jvegaf.musikbox.app;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jvegaf.musikbox.app.controller.MainController;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


public final class MusikBoxApp extends Application {

    private ConfigurableApplicationContext applicationContext;
    private FxWeaver fxWeaver;
    private Scene mainScene;

    @Override
    public void init(){
        applicationContext = new SpringApplicationBuilder()
                .sources(Main.class)
                .run(getParameters().getRaw().toArray(new String[0]));

        fxWeaver = applicationContext.getBean(FxWeaver.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = fxWeaver.loadView(MainController.class);
        mainScene = new Scene(root, 1440, 800);
        stage.setScene(mainScene);
        stage.show();

    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
