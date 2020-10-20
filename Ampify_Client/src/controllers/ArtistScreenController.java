package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Artist;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArtistScreenController implements Initializable {


    private Artist artist;
    private Pane homePageDisplayPane;

    @FXML
    public ImageView imageView;
    @FXML
    public Label ratingLabel;
    @FXML
    public Label nameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveArtistDetails(Artist artist, Pane homePageDisplayPane) {
        this.artist = artist;
        this.homePageDisplayPane = homePageDisplayPane;

        nameLabel.setText(artist.getArtistName());
        ratingLabel.setText("⭐" + String.valueOf(artist.getArtistRating()));
        imageView.setImage(new Image(artist.getArtistImageURL()));
        imageView.setPreserveRatio(false);
//        imageView.setStyle("-fx-background-image: url(\"" + artist.getArtistImageURL() + "\"); -fx-background-size: cover;");
    }


    public void onBackClicked(ActionEvent actionEvent) {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            homePageDisplayPane.getChildren().remove(0);
            homePageDisplayPane.getChildren().add(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
