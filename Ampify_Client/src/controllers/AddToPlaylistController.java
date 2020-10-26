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

    public void onRefreshClicked() {
        loadMyPlaylists();
    }

    public void onAddClicked() {

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

        try{
            Parent createPlaylistScreen = FXMLLoader.load(getClass().getResource("/resources/fxml/createPlaylistScreen.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(createPlaylistScreen));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
