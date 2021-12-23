package me.jvegaf.musikbox.ui;

import com.gluonhq.ignite.guice.GuiceContext;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jvegaf.musikbox.ui.views.MainViewController;

import java.io.IOException;
import java.util.Arrays;

public final class GUI extends Application {

    private GuiceContext guiceContext;


    @Override
    public void start(Stage primaryStage) throws Exception {
        guiceContext = new GuiceContext(this, () -> Arrays.asList(new GUIConfig()));
        guiceContext.init();
        CSSFX.start();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MainView.fxml"));
        loader.setController(guiceContext.getInstance(MainViewController.class));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(root == null) {
            throw new IllegalStateException("There was likely an error in the controller initialize() method.");
        }
        Scene mainScene = new Scene(root, 1440, 800);
        mainScene.getStylesheets().add("/styles/dark.css");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Musikbox");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}
