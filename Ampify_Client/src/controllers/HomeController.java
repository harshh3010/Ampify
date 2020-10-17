package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import utilities.UserApi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    public Label userEmailLabel;
    @FXML
    private HBox musicCardHBox;
    UserApi userApi = UserApi.getInstance();

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
    }

    private void displayUserData(){
        System.out.println("display " + userApi.getEmail());
        userEmailLabel.setText(userApi.getEmail());
    }
}
