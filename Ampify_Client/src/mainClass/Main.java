package mainClass;

import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import utilities.UserApi;

import java.net.Socket;
import java.io.*;
import java.util.prefs.Preferences;

public class Main extends Application {

    public static String serverIp = "localhost";
    public static int serverPort = 50002;

    public static Socket userSocket;
    public static ObjectOutputStream userOutputStream;
    public static ObjectInputStream userInputStream;

    public static User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;

        Preferences pref;
        pref = Preferences.userNodeForPackage(LoginController.class);
        String isLoggedIn = pref.get("isLoggedIn", "FALSE");

        if (isLoggedIn.equals("TRUE")) {
            // User already logged in
            String email = pref.get("email", null);
            if (email == null) {
                // Login info not present
                root = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
                goToScreen(primaryStage, root);
            } else {
                // Login info present
                // Save email in UserApi class
                UserApi userApi = UserApi.getInstance();
                userApi.setEmail(email);
                System.out.println(email);
                root = FXMLLoader.load(getClass().getResource("/resources/fxml/home.fxml"));
                goToScreen(primaryStage, root);
            }
        } else {
            root = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
            goToScreen(primaryStage, root);
        }
    }

    public void goToScreen(Stage primaryStage, Parent root) {
        primaryStage.setTitle("Ampify-Player");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
