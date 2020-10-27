package mainClass;

import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import serverClasses.requests.ChoicesFetchRequest;
import utilities.UserApi;

import java.net.Socket;
import java.io.*;
import java.util.prefs.Preferences;

public class Main extends Application {

    // Server's IP Address and Port number
    public static String serverIp = "localhost";
    public static int serverPort = 50019;

    // Declaring the user socket and input output streams
    public static Socket userSocket;
    public static ObjectOutputStream userOutputStream;
    public static ObjectInputStream userInputStream;

    public static User user;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Initializing user socket and input output streams
        userSocket = new Socket(serverIp, serverPort);
        userOutputStream = new ObjectOutputStream(userSocket.getOutputStream());
        userInputStream = new ObjectInputStream(userSocket.getInputStream());

        Parent root;

        // Checking the login  info of user from registry
        Preferences pref = Preferences.userNodeForPackage(LoginController.class);
        String isLoggedIn = pref.get("isLoggedIn", "FALSE");

        if (isLoggedIn.equals("TRUE")) {    // User already logged in

            // If user is already login status is true then fetching email from registry
            String email = pref.get("email", null);

            if (email == null || email.isEmpty()) {

                // If email not found, then redirecting the user to login screen
                root = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
                goToScreen(primaryStage, root);

            } else {

                // If email found then saving it in UserApi class
                UserApi userApi = UserApi.getInstance();
                userApi.setEmail(email);

                try {

                    // Fetching user's choices from the database
                    ChoicesFetchRequest choicesFetchRequest = new ChoicesFetchRequest(userApi.getEmail());
                    userOutputStream.writeObject(choicesFetchRequest);
                    userOutputStream.flush();
                    choicesFetchRequest = (ChoicesFetchRequest) userInputStream.readObject();

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
                        root = FXMLLoader.load(getClass().getResource("/resources/fxml/home.fxml"));
                        goToScreen(primaryStage, root);

                    } else {

                        // If user's choices not found then redirect the user to choices screen
                        root = FXMLLoader.load(getClass().getResource("/resources/fxml/choicesScreen.fxml"));
                        goToScreen(primaryStage, root);

                    }

                } catch (Exception e) {
                    System.out.println("Error establishing connection with the server!");
                    e.printStackTrace();
                }

            }
        } else {

            // If login status was false then redirecting the user to login screen
            root = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
            goToScreen(primaryStage, root);

        }
    }

    // Function to redirect the user to another screen
    public void goToScreen(Stage primaryStage, Parent root) {
        primaryStage.setTitle("Ampify-Player");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}