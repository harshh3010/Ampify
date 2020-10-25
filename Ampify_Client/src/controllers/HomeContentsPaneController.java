package controllers;

import CellFactories.ArtistCellFactory;
import CellFactories.MusicCardFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import model.Artist;
import model.Song;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeContentsPaneController implements Initializable {

    public HBox musicCardHBox;
    public JFXListView<Artist> popularArtistsListView;
    public JFXListView<Song> recentlyPlayedListView;
    public JFXListView<Song> recentlyAddedListView;
    public JFXListView<Song> recommendedSongsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Displaying top artists
        try {
            List<Artist> artists = AmpifyServices.getTopArtists();
            popularArtistsListView.setItems(FXCollections.observableArrayList(artists));
            popularArtistsListView.setCellFactory(new ArtistCellFactory(HomeScreenWidgets.displayPane));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying recently added songs
        try {
            List<Song> songs = AmpifyServices.getRecentSongs();
            recentlyAddedListView.setItems(FXCollections.observableArrayList(songs));
            recentlyAddedListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*
        Displaying recommended songs to the user (Based on choice of Artists, Languages, Genres
         */
        try{
            List<Song> songs = AmpifyServices.getUserChoiceSongs();
            recommendedSongsListView.setItems(FXCollections.observableArrayList(songs));
            recommendedSongsListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
