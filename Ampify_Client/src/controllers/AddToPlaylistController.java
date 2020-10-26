package controllers;

import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Playlist;
import model.Song;

import java.io.IOException;
import java.util.List;

public class AddToPlaylistController {

    @FXML
    public JFXListView<Playlist> playlistListView;

    private Song song;

    public void getSongToAdd(Song song) {
        this.song = song;

        loadMyPlaylists();
    }

    private void loadMyPlaylists() {
        try {
            List<Playlist> playlists = AmpifyServices.getMyPlaylists();
            playlistListView.setItems(FXCollections.observableArrayList(playlists));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onRefreshClicked(ActionEvent actionEvent) {
        loadMyPlaylists();
    }

    public void onAddClicked(ActionEvent actionEvent) {

        Playlist selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        try {
            String result = AmpifyServices.addSongToPlaylist(selectedPlaylist.getId(), song.getSongID());
            if (result.equals("SUCCESS")) {
                // TODO: DISPLAY SUCCESS
                System.out.println("Song added successfully!");

                // CLOSING THE SCREEN
                Stage stage = (Stage) playlistListView.getScene().getWindow();
                stage.close();
            } else if (result.equals("ALREADY_EXIST")) {
                // TODO: DISPLAY ERROR
                System.out.println("Song already present in the playlist!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void onCreateNewClicked() {

    }
}
