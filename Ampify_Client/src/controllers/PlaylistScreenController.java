package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Playlist;
import model.Song;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.util.List;

public class PlaylistScreenController {

    @FXML
    public JFXListView<Song> songsListView;
    @FXML
    public Label nameLabel;

    // Function to fetch playlist object from previous screen
    public void getPlaylistDetails(Playlist playlist) {

        // Displaying the name of playlist
        nameLabel.setText(playlist.getPlaylistName());

        // Loading the songs of playlist from server
        try {
            List<Song> songList = AmpifyServices.getSongsOfPlaylist(playlist.getId());
            songsListView.setItems(FXCollections.observableArrayList(songList));
            songsListView.setCellFactory(new SongCellFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Called on click of back button
    public void onBackPressed() {

        // Redirecting the user to home screen
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            HomeScreenWidgets.displayPane.getChildren().remove(0);
            HomeScreenWidgets.displayPane.getChildren().add(newPane);

            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}