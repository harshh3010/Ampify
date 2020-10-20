package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import mainClass.Main;
import model.Album;
import model.Artist;
import model.Song;
import serverClasses.requests.AlbumFetchRequest;
import serverClasses.requests.ArtistFetchRequest;
import serverClasses.requests.SongFetchRequest;
import utilities.ArtistsAlbumFetchType;
import utilities.SongFetchType;
import utilities.UserApi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class HomeController implements Initializable {

    @FXML
    public Label userEmailLabel;
    @FXML
    public JFXButton logoutButton;
    @FXML
    private HBox musicCardHBox;
    UserApi userApi = UserApi.getInstance();

    private ObjectOutputStream oos = Main.userOutputStream;
    private ObjectInputStream ois = Main.userInputStream;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUserData();

        // TODO: Recently played music
        Node[] nodes = new Node[10];

        for (int i = 0; i < nodes.length; i++) {
            try{
                nodes[i] = FXMLLoader.load(getClass().getResource("/resources/fxml/music_card.fxml"));
                musicCardHBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//fetching top artists
        try {
            ArtistFetchRequest artistsFetchRequest = new ArtistFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
            oos.writeObject(artistsFetchRequest);
            oos.flush();
            ObjectInputStream ois = Main.userInputStream;
            List<Artist> artists = (List<Artist>) ois.readObject();

            System.out.println("!!!TOP ARTISTS\n \n");
            for(Artist artist:artists){
                System.out.println(artist.getArtistName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//fetching top albums
        try {
            AlbumFetchRequest albumFetchRequest = new AlbumFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
            oos.writeObject(albumFetchRequest);
            oos.flush();
            ObjectInputStream ois = Main.userInputStream;
            List<Album> topAlbums = (List<Album>) ois.readObject();
            //iterating the topAlbums
            System.out.println("!!!TOP Albums\n \n");
            for(Album album:topAlbums){
                System.out.println(album.getAlbumName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        *for fetching top songs
        */

        try {
            //fetching top songs based on the rating
            SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.TOP));
            oos.writeObject(songFetchRequest);
            oos.flush();
            ObjectInputStream ois = Main.userInputStream;
            List<Song> topSongs = (List<Song>) ois.readObject();
            //iterating the topSongs
            System.out.println("\n\n!!!TOP Songs\n \n");
            for(Song songs:topSongs){
                System.out.println(songs.getSongName()+" "+songs.getAlbumID()+" "+songs.getSongLyricsURL());
            }

        } catch (Exception e) {
            //printing errors if any
            e.printStackTrace();
        }



        System.out.print("!!!");
        System.out.println("\n\nParticular Artist Songs\n\n");
        /*
         *for fetching songs of particular artist
         */
        try {
            //fetching songs of a particular artist based on the rating
            //TODO : GIVE ARTIST ID USING getArtistID of artist class for artist which is selected
            System.out.println("Songs of idartist::");
            SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST),3);
            oos.writeObject(songFetchArtistRequest);
            oos.flush();
            ObjectInputStream ois = Main.userInputStream;
            List<Song> songsOfParticularArtist = (List<Song>) ois.readObject();
            //iterating the topSongs
            for(Song songs:songsOfParticularArtist){
                System.out.println(songs.getSongName()+" "+songs.getAlbumID()+" "+songs.getSongLyricsURL());
            }

        } catch (Exception e) {
            //printing errors if any
            e.printStackTrace();
        }
    }

    private void displayUserData(){
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
        preferences.put("isLoggedIn","FALSE");
        preferences.put("email","");

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
