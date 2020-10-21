package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Artist;
import model.Song;

import java.io.IOException;
import java.util.List;


public class ArtistScreenController {

    private Artist artist;
    private Pane homePageDisplayPane;

    @FXML
    public ImageView imageView;
    @FXML
    public Label ratingLabel;
    @FXML
    public Label nameLabel;
    @FXML
    public JFXListView<Song> songListView;

    public void saveArtistDetails(Artist artist, Pane homePageDisplayPane) {
        this.artist = artist;
        this.homePageDisplayPane = homePageDisplayPane;

        nameLabel.setText(artist.getArtistName());
        ratingLabel.setText("⭐" + String.valueOf(artist.getArtistRating()));
        imageView.setImage(new Image(artist.getArtistImageURL()));
        imageView.setPreserveRatio(false);
//        imageView.setStyle("-fx-background-image: url(\"" + artist.getArtistImageURL() + "\"); -fx-background-size: cover;");

        try {
            List<Song> songList = AmpifyServices.getSongsOfArtist(artist.getArtistID());
            for (Song song : songList) {
                System.out.println(song.getSongName()+" "+song.getSongURL()+" "+song.getArtistName());
            }
            songListView.setItems(FXCollections.observableArrayList(songList));
            songListView.setCellFactory(new SongCellFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
