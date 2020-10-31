package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Album;
import model.Song;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.util.List;

public class AlbumScreenController {

    @FXML
    public Label nameLabel;
    @FXML
    public JFXListView<Song> songsListView;
    @FXML
    public Label releaseLabel;
    @FXML
    public Label ratingLabel;
    private Album album;
    private int offset;
    private int rowCount;

    public void setAlbumDetails(Album album) {
        this.album = album;

        // Initially setting the offset to 0
        offset = 0;

        // Setting the rowCount to 10, i.e loading 10 items at a time
        rowCount = 10;

        nameLabel.setText(album.getAlbumName());
        releaseLabel.setText(album.getReleaseDate());
        ratingLabel.setText("⭐" + album.getRating());

        loadItems();
    }
    private void loadItems() {

        HomeScreenWidgets.showProgressIndicator();

        new Thread(() -> {

            try {
                List<Song> songs = AmpifyServices.getSongsOfAlbum(album.getAlbumID(), offset, rowCount);
                songsListView.setItems(FXCollections.observableArrayList(songs));
                songsListView.setCellFactory(new SongCellFactory());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Platform.runLater(HomeScreenWidgets::hideProgressIndicator);

        }).start();

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
        if (!songsListView.getItems().isEmpty()) {
            offset = offset + rowCount;
            loadItems();
        }
    }
}
