package controllers;

import CellFactories.AlbumCardFactory;
import CellFactories.ArtistCellFactory;
import CellFactories.MusicCardFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import model.Album;
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
    public JFXListView<Song> topSongsListView;
    public JFXListView<Album> topAlbumsListView;

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

        // Displaying recently played songs
        try {
            List<Song> songs = AmpifyServices.getUserRecentlyPlayedSong();
            recentlyPlayedListView.setItems(FXCollections.observableArrayList((songs)));
            recentlyPlayedListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying recently added songs
        try {
            List<Song> songs = AmpifyServices.getRecentAddedSongs();
            recentlyAddedListView.setItems(FXCollections.observableArrayList(songs));
            recentlyAddedListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Displaying recommended songs to the user (Based on choice of Artists, Languages, Genres)
        try {
            List<Song> songs = AmpifyServices.getUserChoiceSongs();
            recommendedSongsListView.setItems(FXCollections.observableArrayList(songs));
            recommendedSongsListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying the top(10) songs to the user
        try {
            List<Song> songs = AmpifyServices.getTopSongs();
            topSongsListView.setItems(FXCollections.observableArrayList(songs));
            topSongsListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying top albums to the user
        try {
            List<Album> albums = AmpifyServices.getTopAlbums();
            topAlbumsListView.setItems(FXCollections.observableArrayList(albums));
            topAlbumsListView.setCellFactory(new AlbumCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
