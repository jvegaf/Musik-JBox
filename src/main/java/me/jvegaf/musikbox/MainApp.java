package me.jvegaf.musikbox;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jvegaf.musikbox.ui.views.MainViewController;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        CSSFX.start();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MainView.fxml"));
        loader.setController(new MainViewController());
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root == null) return;
        Scene mainScene = new Scene(root, 1440, 800);
        mainScene.getStylesheets().add("/styles/dark.css");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Musikbox");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}
