package me.jvegaf.musikbox.app;


import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jvegaf.musikbox.app.controller.MainController;
import me.jvegaf.musikbox.context.tracks.infrastructure.file.CollectFilesCommand;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;


public final class MusikBoxApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(){
        applicationContext = new SpringApplicationBuilder()
                .sources(Main.class)
                .run(getParameters().getRaw().toArray(new String[0]));

    }

    @Override
    public void start(Stage stage) throws Exception {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root, 1440, 800);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
