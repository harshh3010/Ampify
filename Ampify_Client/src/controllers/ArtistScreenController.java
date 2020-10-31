package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Artist;
import model.Song;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.util.List;

public class ArtistScreenController {

    @FXML
    public ImageView imageView;
    @FXML
    public Label ratingLabel;
    @FXML
    public Label nameLabel;
    @FXML
    public JFXListView<Song> songListView;

    private Artist artist;
    private int offset, rowCount;

    public void saveArtistDetails(Artist artist) {
        this.artist = artist;

        offset = 0;
        rowCount = 10;

        nameLabel.setText(artist.getArtistName());
        ratingLabel.setText("⭐" + artist.getArtistRating());
        imageView.setImage(new Image(artist.getArtistImageURL()));
        imageView.setPreserveRatio(false);

        loadItems();
    }

    private void loadItems() {

        HomeScreenWidgets.showProgressIndicator();

        new Thread(() -> {

            try {
                List<Song> songList = AmpifyServices.getSongsOfArtist(artist.getArtistID(), offset, rowCount);
                songListView.setItems(FXCollections.observableArrayList(songList));
                songListView.setCellFactory(new SongCellFactory());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Platform.runLater(HomeScreenWidgets::hideProgressIndicator);
        }).start();

    }

    public void onBackClicked() {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            HomeScreenWidgets.displayPane.getChildren().remove(0);
            HomeScreenWidgets.displayPane.getChildren().add(newPane);

            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Called on click of previous button
    public void onPreviousClicked() {
        // Fetching the previous batch only if offset is not 0 (Offset = 0 specifies first batch)
        if (offset > 0) {
            offset = offset - rowCount;
            loadItems();
        }
    }

    // Called on click of next button
    public void onNextClicked() {
        // Fetching the next batch only if current one is non-empty (Empty specifies the end)
        if (!songListView.getItems().isEmpty()) {
            offset = offset + rowCount;
            loadItems();
        }
    }
}
