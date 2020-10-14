package mainClass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.net.Socket;
import  java.io.*;

public class Main extends Application {

    public static String serverIp = "localhost";
    public static int serverPort = 50000;

    public static Socket userSocket;
    public static ObjectOutputStream userOutputStream;
    public static ObjectInputStream userInputStream;
    public static OutputStream os;
    public static InputStream is;
    /*using this object we will access info of logged in user
    * user class extends user_auth class so u can access email, userloginstatus(see in User class) of user  */
    public static User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/home.fxml"));
        primaryStage.setTitle("Ampify-Player");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
