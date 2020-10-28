package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mainClass.Main;
import serverClasses.requests.ChoicesFetchRequest;
import utilities.UserApi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SplashScreenController implements Initializable {

    @FXML
    public Label ampifyLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Parent parent = null;

                    // Checking the login  info of user from registry
                    Preferences pref = Preferences.userNodeForPackage(LoginController.class);
                    String isLoggedIn = pref.get("isLoggedIn", "FALSE");

                    if (isLoggedIn.equals("TRUE")) {    // User already logged in

                        // If user is already login status is true then fetching email from registry
                        String email = pref.get("email", null);

                        if (email == null || email.isEmpty()) {

                            // If email not found, then redirecting the user to login screen
                            parent = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));

                        } else {

                            // If email found then saving it in UserApi class
                            UserApi userApi = UserApi.getInstance();
                            userApi.setEmail(email);

                            try {

                                // Fetching user's choices from the database
                                ChoicesFetchRequest choicesFetchRequest = new ChoicesFetchRequest(userApi.getEmail());
                                Main.userOutputStream.writeObject(choicesFetchRequest);
                                Main.userOutputStream.flush();
                                choicesFetchRequest = (ChoicesFetchRequest) Main.userInputStream.readObject();

                                if (choicesFetchRequest != null
                                        && !choicesFetchRequest.getArtistList().isEmpty()
                                        && !choicesFetchRequest.getLanguageList().isEmpty()
                                        && !choicesFetchRequest.getGenresList().isEmpty()
                                ) {

                                    // If choices present in database save them in UserApi class
                                    userApi.setLikedLanguages(choicesFetchRequest.getLanguageList());
                                    userApi.setLikedGenres(choicesFetchRequest.getGenresList());
                                    userApi.setLikedArtists(choicesFetchRequest.getArtistList());

                                    // Redirecting the user to home screen
                                    parent = FXMLLoader.load(getClass().getResource("/resources/fxml/home.fxml"));

                                } else {

                                    // If user's choices not found then redirect the user to choices screen
                                    parent = FXMLLoader.load(getClass().getResource("/resources/fxml/choicesScreen.fxml"));

                                }

                            } catch (Exception e) {
                                System.out.println("Error establishing connection with the server!");
                                e.printStackTrace();
                            }

                        }
                    } else {

                        // If login status was false then redirecting the user to login screen
                        parent = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));

                    }

                    Parent finalParent = parent;
                    Platform.runLater(() -> goToScreen(finalParent));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    // Function to move to another screen
    private void goToScreen(Parent parent) {

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ampifyLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
