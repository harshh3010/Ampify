package controllers;

import Services.MediaPlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import model.Song;

import java.io.IOException;

public class SongsQueueCellController extends ListCell<Song> {

    @FXML
    private Label nameLabel;

    public SongsQueueCellController(){
        loadFXML();
    }

    private void loadFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/songsQueueCard.fxml"));
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

            // Displaying the name of song
            nameLabel.setText(song.getSongName());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
