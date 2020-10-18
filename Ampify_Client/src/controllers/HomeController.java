package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Artist;
import serverClasses.requests.ArtistFetchRequest;
import utilities.ArtistsFetchType;
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

        try {
            ArtistFetchRequest artistsFetchRequest = new ArtistFetchRequest(String.valueOf(ArtistsFetchType.TOP));
            oos.writeObject(artistsFetchRequest);
            oos.flush();
            ObjectInputStream ois = Main.userInputStream;
            List<Artist> artists = (List<Artist>) ois.readObject();

            for(Artist artist:artists){
                System.out.println(artist.getArtistName());
            }

        } catch (Exception e) {
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
