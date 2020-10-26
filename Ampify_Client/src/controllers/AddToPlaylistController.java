package controllers;

import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Playlist;
import model.Song;

import java.io.IOException;
import java.util.List;

public class AddToPlaylistController {

    @FXML
    public JFXListView<Playlist> playlistListView;
    private Song song;

    // Function to fetch the song object from previous screen
    public void getSongToAdd(Song song) {
        this.song = song;

        // Loading the user's playlists
        loadMyPlaylists();
    }

    // Function to load user's playlists
    private void loadMyPlaylists() {

        // Displaying the playlists in list view
        try {
            List<Playlist> playlists = AmpifyServices.getMyPlaylists();
            playlistListView.setItems(FXCollections.observableArrayList(playlists));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Called when refresh button clicked
    public void onRefreshClicked() {

        // Reloading the user's playlists
        loadMyPlaylists();
    }

    // Called on click of add button
    public void onAddClicked() {

        // Getting the selected playlist
        Playlist selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {

            // Adding the song to selected playlist
            try {
                // TODO: DISPLAY DIALOG
                // Fetching the response from server
                String result = AmpifyServices.addSongToPlaylist(selectedPlaylist.getId(), song.getSongID());

                if (result.equals("SUCCESS")) {

                    // Closing the add to playlist screen on success
                    Stage stage = (Stage) playlistListView.getScene().getWindow();
                    stage.close();

                } else if (result.equals("ALREADY_EXIST")) {

                    // Displaying the error in case of failure
                    System.out.println("Song already present in the playlist!");

                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            // TODO: DISPLAY ERROR
            System.out.println("No playlist selected!");
        }

    }

    // Called on click of create new button
    public void onCreateNewClicked() {

        // Displaying the create playlist screen in a new window
        try {
            Parent createPlaylistScreen = FXMLLoader.load(getClass().getResource("/resources/fxml/createPlaylistScreen.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(createPlaylistScreen));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
