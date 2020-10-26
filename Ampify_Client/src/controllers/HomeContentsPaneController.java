package controllers;

import CellFactories.AlbumCardFactory;
import CellFactories.ArtistCellFactory;
import CellFactories.MusicCardFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import model.Album;
import model.Artist;
import model.Playlist;
import model.Song;
import serverClasses.requests.SongListType;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeContentsPaneController implements Initializable {

    public JFXListView<Artist> popularArtistsListView;
    public JFXListView<Song> recentlyPlayedListView;
    public JFXListView<Song> recentlyAddedListView;
    public JFXListView<Song> recommendedSongsListView;
    public JFXListView<Song> topSongsListView;
    public JFXListView<Album> topAlbumsListView;
    public JFXListView<Playlist> personalPlaylistListView;
    public JFXListView<Playlist> groupPlaylistListView;

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
            List<Song> songs = AmpifyServices.getRecentAddedSongs(0, 10);
            recentlyAddedListView.setItems(FXCollections.observableArrayList(songs));
            recentlyAddedListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Displaying recommended songs to the user (Based on choice of Artists, Languages, Genres)
        try {
            List<Song> songs = AmpifyServices.getUserChoiceSongs(0, 10);
            recommendedSongsListView.setItems(FXCollections.observableArrayList(songs));
            recommendedSongsListView.setCellFactory(new MusicCardFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying the top(10) songs to the user
        try {
            List<Song> songs = AmpifyServices.getTopSongs(0, 10);
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

        // Loading user's playlists
        try {
            List<Playlist> playlists = AmpifyServices.getMyPlaylists();
            List<Playlist> personalPlaylists = new ArrayList<>();
            List<Playlist> groupPlaylists = new ArrayList<>();
            for (Playlist playlist : playlists) {
                if (playlist.getCategory().equals("GROUP")) {
                    groupPlaylists.add(playlist);
                } else {
                    personalPlaylists.add(playlist);
                }
            }
            personalPlaylistListView.setItems(FXCollections.observableArrayList(personalPlaylists));
            groupPlaylistListView.setItems(FXCollections.observableArrayList(groupPlaylists));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Called when view all button for recently played songs clicked
    public void onViewAllRecentlyPlayed() {

        // Redirect the user to history screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/historyScreen.fxml"));
            Pane newLoadedPane = loader.load();
            HomeScreenWidgets.displayPane.getChildren().clear();
            HomeScreenWidgets.displayPane.getChildren().add(newLoadedPane);
            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.HISTORY_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Called when view all button for recently added songs clicked
    public void onViewAllRecentlyAdded() {

        // Redirect the user to recently added songs screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/songsListScreen.fxml"));
            Pane newLoadedPane = loader.load();
            SongsListScreenController songsListScreenController = loader.getController();
            songsListScreenController.getFetchType(SongListType.RECENTLY_ADDED_SONGS);
            HomeScreenWidgets.displayPane.getChildren().clear();
            HomeScreenWidgets.displayPane.getChildren().add(newLoadedPane);
            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.SONG_LIST_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Called when view all button for recommended songs clicked
    public void onViewAllRecommendedSongs() {

        // Redirect the user to recommended songs screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/songsListScreen.fxml"));
            Pane newLoadedPane = loader.load();
            SongsListScreenController songsListScreenController = loader.getController();
            songsListScreenController.getFetchType(SongListType.RECOMMENDED_SONGS);
            HomeScreenWidgets.displayPane.getChildren().clear();
            HomeScreenWidgets.displayPane.getChildren().add(newLoadedPane);
            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.SONG_LIST_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Called when view all button for top songs clicked
    public void onViewAllTopSongs() {

        // Redirect the user to top songs screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/songsListScreen.fxml"));
            Pane newLoadedPane = loader.load();
            SongsListScreenController songsListScreenController = loader.getController();
            songsListScreenController.getFetchType(SongListType.TOP_SONGS);
            HomeScreenWidgets.displayPane.getChildren().clear();
            HomeScreenWidgets.displayPane.getChildren().add(newLoadedPane);
            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.SONG_LIST_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
