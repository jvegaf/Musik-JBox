package me.jvegaf.musikbox.app.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class DetailController {

    @FXML
    private ImageView artworkImageView;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField artistTextField;
    @FXML
    private TextField albumTextField;
    @FXML
    private TextField genreTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField bpmTextField;
    @FXML
    private TextArea  commentsTextField;
    @FXML
    private TextField keyTextField;
    @FXML
    private Label     titleLabel;
    @FXML
    private Label     artistLabel;
    @FXML
    private Label     albumLabel;
    @FXML
    private Button    saveBtn;
    @FXML
    private Button    cancelBtn;

    private final CommandBus commandBus;
    private final QueryBus   queryBus;
    private Stage stage;

    @FXML
    public void initialize() {
    }

    @Autowired
    public DetailController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus   = queryBus;
    }



    private void closeActionListener() {
        Stage stage = (Stage) this.cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void saveActionListener(Track t) {


//        t.setArtist(this.artistTextField.getText());
//        t.setName(this.titleTextField.getText());
//        t.setAlbum(this.albumTextField.getText());
//        t.setGenre(this.genreTextField.getText());
//        t.setYear(this.yearTextField.getText());
//        t.setBpm(this.bpmTextField.getText());
//        t.setKey(this.keyTextField.getText());
//        t.setComments(this.commentsTextField.getText());
//        // TODO: implement this.track.setArtworkData
//        this.oldTracksRepository.updateTrack(t);
        this.closeActionListener();
    }

    public void setTrack(Track track) {
        this.cancelBtn.setOnMouseClicked(event -> closeActionListener());
        this.saveBtn.setOnMouseClicked(event -> saveActionListener(track));
        this.titleTextField.setText(track.title().value());
        this.artistTextField.setText(track.artist().value());
        this.albumTextField.setText(track.album().value());
        this.genreTextField.setText(track.genre().value());
        this.yearTextField.setText(track.year().value());
        this.bpmTextField.setText(track.bpm().value().toString());
        this.keyTextField.setText(track.key().value());
        this.commentsTextField.setText(track.comments().value());

        this.titleLabel.textProperty().bind(this.titleTextField.textProperty());
        this.artistLabel.textProperty().bind(this.artistTextField.textProperty());
        this.albumLabel.textProperty().bind(this.albumTextField.textProperty());
//
//        if (track.getArtworkData().length < 1) {
//            return;
//        }

//        this.artworkImageView.setImage(new Image(new ByteArrayInputStream(track.getArtworkData())));
    }
}
