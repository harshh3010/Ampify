package controllers;

import Services.MediaPlayerService;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.HomeScreenWidgets;
import utilities.UserApi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class HomeController implements Initializable {

    @FXML
    public Label userEmailLabel;
    @FXML
    public JFXButton logoutButton;
    @FXML
    public Pane displayPane;
    @FXML
    public Pane bottomPane;
    @FXML
    UserApi userApi = UserApi.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUserData();

        HomeScreenWidgets.displayPane = displayPane;
        HomeScreenWidgets.bottomPane = bottomPane;

        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            displayPane.getChildren().add(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (MediaPlayerService.previousSong != null) {
            try {
                Pane mediaController = FXMLLoader.load(getClass().getResource("/resources/fxml/mediaPlayer.fxml"));
                bottomPane.getChildren().add(mediaController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        // Displaying top albums
//        try {
//            System.out.println("TOP ALBUMS: ");
//            List<Album> albums = AmpifyServices.getTopAlbums();
//            for (Album album : albums) {
//                System.out.println(album.getAlbumName());
//            }
//            System.out.println();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        // Displaying Top Songs
//        try {
//            System.out.println("TOP SONGS: ");
//            List<Song> songs = AmpifyServices.getTopSongs();
//            for (Song song : songs) {
//                System.out.println(song.getSongURL());
//            }
//            System.out.println();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    private void displayUserData() {
        System.out.println("display " + userApi.getEmail());
        userEmailLabel.setText(userApi.getEmail());
    }

    public void onLogoutClicked(ActionEvent actionEvent) throws IOException {

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
