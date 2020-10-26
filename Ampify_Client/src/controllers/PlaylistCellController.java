package controllers;

import Services.MediaPlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import model.Playlist;

import java.io.IOException;

public class PlaylistCellController extends ListCell<Playlist> {

    @FXML
    private Text nameLabel;    // Label to display playlist name
    @FXML
    private Button menuButton;  // Button to open a popup menu

    public PlaylistCellController() {
        loadFXML();
    }

    /*
    Function to load the fxml file for displaying the playlist
    */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/playlistCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Playlist playlist, boolean b) {
        super.updateItem(playlist, b);

        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            // Displaying the name of playlist
            nameLabel.setText(playlist.getPlaylistName());

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

    }
}
