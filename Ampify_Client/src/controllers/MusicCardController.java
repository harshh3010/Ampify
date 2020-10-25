package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.Song;

import java.io.IOException;

public class MusicCardController extends ListCell<Song> {

    @FXML
    private Label nameLabel;
    @FXML
    private AnchorPane mainCard;

    public MusicCardController(){
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/music_card.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Song song, boolean b) {
        super.updateItem(song, b);
        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            // Setting the name of song in the label
            nameLabel.setText(song.getSongName());

            // Creating a background using image of song
//            BackgroundImage backgroundImage = new BackgroundImage(
//                    new Image(song.getSongImageURL()),
//                    BackgroundRepeat.REPEAT,
//                    BackgroundRepeat.NO_REPEAT,
//                    BackgroundPosition.CENTER,
//                    new BackgroundSize(100, 100, true, true, false, true));

            // Setting the background in main artist card
//            mainCard.setBackground(new Background(backgroundImage));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
