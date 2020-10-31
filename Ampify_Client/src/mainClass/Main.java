package mainClass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.net.Socket;
import java.io.*;

public class Main extends Application {

    // Server's IP Address and Port number
    public static String serverIp = "localhost";
    public static int serverPort = 5018;

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

        // Displaying the splash screen on start of application
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/splashScreen.fxml"));
        goToScreen(primaryStage, root);

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