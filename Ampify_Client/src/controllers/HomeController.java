package controllers;

import Services.AmpifyServices;
import Services.MediaPlayerService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.Song;
import utilities.HomeScreenWidgets;
import utilities.UserApi;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class HomeController implements Initializable {

    public Label userEmailLabel;
    public JFXButton logoutButton;
    public Pane displayPane;
    public Pane bottomPane;
    public JFXListView<Song> nowPlayingList;
    UserApi userApi = UserApi.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUserData();

        HomeScreenWidgets.displayPane = displayPane;
        HomeScreenWidgets.bottomPane = bottomPane;
        HomeScreenWidgets.nowPlayingList = nowPlayingList;

        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            displayPane.getChildren().add(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Displaying the last played song in bottomPane
        try {
            MediaPlayerService.previousSong = AmpifyServices.getUserLastPlayedSong();
            if (MediaPlayerService.previousSong != null) {
                try {
                    Pane mediaController = FXMLLoader.load(getClass().getResource("/resources/fxml/mediaPlayer.fxml"));
                    bottomPane.getChildren().add(mediaController);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying top albums
        try {
            AmpifyServices.offsetTopsong = 0;
            System.out.println("TOP ALBUMS: ");
            List<Album> albums = AmpifyServices.getTopAlbums();
            for (Album album : albums) {
                System.out.println(album.getAlbumName());
            }
            System.out.println();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying Top Songs
        try {
            System.out.println("TOP SONGS: ");
            List<Song> songs = AmpifyServices.getTopSongs();
            for (Song song : songs) {
                System.out.println(song.getSongID() + " " + song.getSongURL());
            }
            System.out.println();
            AmpifyServices.offsetTopsong += AmpifyServices.rowcount;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displayUserData() {
        System.out.println("display " + userApi.getEmail());
        userEmailLabel.setText(userApi.getEmail());
    }

    public void onLogoutClicked(ActionEvent actionEvent) throws IOException {

        // TODO: STOP MEDIA PLAYER ON LOGOUT

        // Removing user's info from UserApi class
        UserApi userApi = UserApi.getInstance();
        userApi.setLikedLanguages(null);
        userApi.setLikedArtists(null);
        userApi.setLikedGenres(null);
        userApi.setEmail(null);

        // Removing login info from local storage
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        preferences.put("isLoggedIn", "FALSE");
        preferences.put("email", "");

        goToLoginScreen(actionEvent);
    }

    private void goToLoginScreen(ActionEvent actionEvent) throws IOException {
        // Scene to be displayed
        Parent loginScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
        Scene loginScreenScene = new Scene(loginScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(loginScreenScene);
        window.show();
    }
}
