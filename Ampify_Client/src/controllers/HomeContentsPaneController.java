package controllers;

import CellFactories.AlbumCardFactory;
import CellFactories.ArtistCellFactory;
import CellFactories.MusicCardFactory;
import CellFactories.PlaylistCellFactory;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.Artist;
import model.Playlist;
import model.Song;
import serverClasses.requests.SongListType;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;
import utilities.UserApi;

import java.io.IOException;
import java.net.URL;
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
    public JFXListView<Song> mostPlayedListView;
    public JFXListView<Song> playedAtSameTimeListView;
    public JFXListView<Song> trendingSongsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserApi userApi = UserApi.getInstance();

        new Thread(() -> {
            // Displaying top artists
            popularArtistsListView.setItems(FXCollections.observableArrayList(userApi.getPopularArtists()));
            popularArtistsListView.setCellFactory(new ArtistCellFactory());
        }).start();

        new Thread(() -> {
            // Displaying recently played songs
            recentlyPlayedListView.setItems(FXCollections.observableArrayList((userApi.getRecentlyPlayed())));
            recentlyPlayedListView.setCellFactory(new MusicCardFactory());
        }).start();

        new Thread(() -> {
            // Displaying recently added songs
            recentlyAddedListView.setItems(FXCollections.observableArrayList(userApi.getRecentlyAdded()));
            recentlyAddedListView.setCellFactory(new MusicCardFactory());
        }).start();


        new Thread(() -> {
            // Displaying recommended songs to the user (Based on choice of Artists, Languages, Genres)
            recommendedSongsListView.setItems(FXCollections.observableArrayList(userApi.getRecommendedMusic()));
            recommendedSongsListView.setCellFactory(new MusicCardFactory());
        }).start();

        new Thread(() -> {
            // Displaying the top(4) songs to the user
            topSongsListView.setItems(FXCollections.observableArrayList(userApi.getTopSongs()));
            topSongsListView.setCellFactory(new MusicCardFactory());
        }).start();

        new Thread(() -> {
            // Loading user's playlists
            personalPlaylistListView.setItems(FXCollections.observableArrayList(userApi.getPersonalPlaylists()));
            groupPlaylistListView.setItems(FXCollections.observableArrayList(userApi.getGroupPlaylist()));
            personalPlaylistListView.setCellFactory(new PlaylistCellFactory());
            groupPlaylistListView.setCellFactory(new PlaylistCellFactory());
        }).start();

        new Thread(() -> {
            // Displaying top albums to the user
            topAlbumsListView.setItems(FXCollections.observableArrayList(userApi.getTopAlbums()));
            topAlbumsListView.setCellFactory(new AlbumCardFactory());
        }).start();

        new Thread(() -> {
            // Displaying most played music to the user
            mostPlayedListView.setItems(FXCollections.observableArrayList(userApi.getMostPlayed()));
            mostPlayedListView.setCellFactory(new MusicCardFactory());
        }).start();

        new Thread(() -> {
            // Displaying music played at same time in past
            playedAtSameTimeListView.setItems(FXCollections.observableArrayList(userApi.getPreviouslyPlayed()));
            playedAtSameTimeListView.setCellFactory(new MusicCardFactory());
        }).start();

        new Thread(() -> {
            // Displaying trending songs to the user
            trendingSongsListView.setItems(FXCollections.observableArrayList(userApi.getTrendingSongs()));
            trendingSongsListView.setCellFactory(new MusicCardFactory());
        }).start();
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

    // Called when user clicks invitations button in group playlists screen
    public void onInvitationsClicked() {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/resources/fxml/invitationsScreen.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Called when user clicks liked songs button
    public void onLikedSongsClicked() {

        // Redirect the user to liked songs screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/songsListScreen.fxml"));
            Pane newLoadedPane = loader.load();
            SongsListScreenController songsListScreenController = loader.getController();
            songsListScreenController.getFetchType(SongListType.LIKED_SONGS);
            HomeScreenWidgets.displayPane.getChildren().clear();
            HomeScreenWidgets.displayPane.getChildren().add(newLoadedPane);
            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.SONG_LIST_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
