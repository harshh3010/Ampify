package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private HBox musicCardHBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
