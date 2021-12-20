package me.jvegaf.musikbox;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jvegaf.musikbox.controllers.MainViewController;
import me.jvegaf.musikbox.models.Track;
import me.jvegaf.musikbox.services.LibraryService;
import me.jvegaf.musikbox.services.MusicFileService;
import me.jvegaf.musikbox.services.PlayerService;
import me.jvegaf.musikbox.services.TagService;

import java.io.IOException;

public class MainApp extends Application {

    private LibraryService libraryService;
    private TagService tagService;
    private PlayerService playerService;
    private MusicFileService musicFileService;
    private MainViewController mainViewController;
    private Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        CSSFX.start();
        initServices();
        this.mainViewController = new MainViewController(this);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MainView.fxml"));
        loader.setController(this.mainViewController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root == null) return;
        Scene mainScene = new Scene(root, 1200, 700);
        mainScene.getStylesheets().add("/styles/dark.css");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Musikbox");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void initServices() {
        this.libraryService = new LibraryService();
        this.playerService = new PlayerService();
        this.tagService = new TagService();
        this.musicFileService = new MusicFileService(this.tagService);
    }

    public LibraryService getLibraryService() { return this.libraryService; }

    public TagService getTagService() { return this.tagService; }

    public PlayerService getPlayerService() { return this.playerService; }

    public MusicFileService getMusicFileService() { return this.musicFileService; }

    public void saveTags(Track t) {
        this.tagService.saveTags(t);
        this.libraryService.updateTrack(t);
    }
}
