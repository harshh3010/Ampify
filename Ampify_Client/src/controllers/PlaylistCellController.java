package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Playlist;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;

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

            // Setting mouse click listener on playlist cell
            this.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {   // Ensuring LMB is clicked
                        if (mouseEvent.getClickCount() == 2) {  // Ensuring its a double click

                            // Redirecting the user to playlist screen
                            try {

                                // Loading the playlist screen
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/playlistScreen.fxml"));
                                Parent parent = loader.load();

                                // Sending the playlist object to playlist screen
                                PlaylistScreenController playlistScreenController = loader.getController();
                                playlistScreenController.getPlaylistDetails(playlist);

                                // Displaying the playlist screen
                                HomeScreenWidgets.displayPane.getChildren().clear();
                                HomeScreenWidgets.displayPane.getChildren().add(parent);
                                HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.PLAYLIST_SCREEN;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

    }
}
