package com.github.jvegaf.musikbox.app.controller;

import com.github.jvegaf.musikbox.context.shared.application.RetrieveArtworkQuery;
import com.github.jvegaf.musikbox.context.shared.domain.Artwork;
import com.github.jvegaf.musikbox.context.tracks.application.update.UpdateTrackCommand;
import com.github.jvegaf.musikbox.shared.domain.TrackResponse;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryBus;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@FxmlView
public class DetailController {


    private final CommandBus commandBus;
    private final QueryBus   queryBus;
    @FXML
    private       VBox       dialog;
    @FXML
    private       ImageView  artworkImageView;
    @FXML
    private       TextField  titleTextField;
    @FXML
    private       TextField  artistTextField;
    @FXML
    private       TextField  albumTextField;
    @FXML
    private       TextField  genreTextField;
    @FXML
    private       TextField  yearTextField;
    @FXML
    private       TextField  bpmTextField;
    @FXML
    private       TextArea   commentsTextField;
    @FXML
    private       TextField  keyTextField;
    @FXML
    private       Label      titleLabel;
    @FXML
    private       Label      artistLabel;
    @FXML
    private       Label      albumLabel;
    @FXML
    private       Button     saveBtn;
    @FXML
    private       Button     cancelBtn;
    private       Stage      stage;

    @Autowired
    public DetailController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus   = queryBus;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setTitle("Track Details");
        stage.setResizable(false);
        stage.setScene(new Scene(dialog));
    }

    public void show() {
        stage.show();
    }

    public void setDetails(TrackResponse track, Window owner) {
        Artwork art = (Artwork) queryBus.ask(new RetrieveArtworkQuery(track.location()));
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        this.cancelBtn.setOnMouseClicked(event -> closeActionListener());
        this.saveBtn.setOnMouseClicked(event -> saveActionListener(track));
        this.titleTextField.setText(track.title());
        track.artist()
             .ifPresent(artistTextField::setText);
        track.album()
             .ifPresent(albumTextField::setText);
        track.genre()
             .ifPresent(genreTextField::setText);
        track.year()
             .ifPresent(yearTextField::setText);
        track.bpm()
             .ifPresent(value -> bpmTextField.setText(String.valueOf(value)));
        track.comments()
             .ifPresent(commentsTextField::setText);
        track.key()
             .ifPresent(keyTextField::setText);

        this.titleLabel.textProperty()
                       .bind(this.titleTextField.textProperty());
        this.artistLabel.textProperty()
                        .bind(this.artistTextField.textProperty());
        this.albumLabel.textProperty()
                       .bind(this.albumTextField.textProperty());

        if (art.data().length < 1) {return;}

        this.artworkImageView.setImage(new Image(new ByteArrayInputStream(art.data())));
    }

    private void closeActionListener() {
        Stage
                stage =
                (Stage) this.cancelBtn.getScene()
                                      .getWindow();
        stage.close();
    }

    private void saveActionListener(TrackResponse t) {

        String artist   = this.artistTextField.getText();
        String title    = this.titleTextField.getText();
        String album    = this.albumTextField.getText();
        String genre    = this.genreTextField.getText();
        String year     = this.yearTextField.getText();
        String bpm      = this.bpmTextField.getText();
        String key      = this.keyTextField.getText();
        String comments = this.commentsTextField.getText();
        //        // TODO: implement this.track.setArtworkData
        // TODO: Validate int value of bpmtextfield

        commandBus.dispatch(new UpdateTrackCommand(t.id(),
                                                   title,
                                                   artist,
                                                   album,
                                                   genre,
                                                   year,
                                                   Integer.getInteger(bpm),
                                                   key,
                                                   comments));
        this.closeActionListener();
    }


}
