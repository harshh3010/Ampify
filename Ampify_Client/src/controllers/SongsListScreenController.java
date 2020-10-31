package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Song;
import serverClasses.requests.SongListType;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utilities.HomeScreenWidgets.*;

public class SongsListScreenController {

    @FXML
    public JFXListView<Song> songListView;
    @FXML
    public Label displayLabel;

    // This will decide which songs are to be displayed
    private SongListType songListType;

    // Offset and row count to fetch a bunch of records from server
    private int offset, rowCount;

    public void getFetchType(SongListType songListType) {
        this.songListType = songListType;

        // Initially setting the offset to 0
        offset = 0;

        // Setting the rowCount to 10, i.e loading 10 items at a time
        rowCount = 10;

        // Displaying text in the label according to type of request
        if (songListType == SongListType.RECENTLY_ADDED_SONGS) {
            displayLabel.setText("Newly added");
        } else if (songListType == SongListType.RECOMMENDED_SONGS) {
            displayLabel.setText("Recommended Songs");
        } else if (songListType == SongListType.TOP_SONGS) {
            displayLabel.setText("Top Songs");
        }else if(songListType == SongListType.LIKED_SONGS){
            displayLabel.setText("Favourites");
        }

        // Loading the first batch
        loadItems();

    }

    // Function to load data from the server
    private void loadItems() {
        showProgressIndicator();

        new Thread(() -> {

            List<Song> songs = new ArrayList<>();

            // Loading songs from the server based on type of request made
            try {
                if (songListType == SongListType.RECENTLY_ADDED_SONGS) {
                    songs = AmpifyServices.getRecentAddedSongs(offset, rowCount);
                } else if (songListType == SongListType.RECOMMENDED_SONGS) {
                    songs = AmpifyServices.getUserChoiceSongs(offset, rowCount);
                } else if (songListType == SongListType.TOP_SONGS) {
                    songs = AmpifyServices.getTopSongs(offset, rowCount);
                }
                else if(songListType == SongListType.LIKED_SONGS){
                    songs = AmpifyServices.getUserFavouriteSong(offset,rowCount);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            List<Song> finalSongs = songs;
            Platform.runLater(() -> {
                songListView.setItems(FXCollections.observableArrayList(finalSongs));
                songListView.setCellFactory(new SongCellFactory());
                HomeScreenWidgets.hideProgressIndicator();
            });

        }).start();
    }

    // Called on click of back button
    public void onBackClicked() {

        // Redirecting the user to home page
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            displayPane.getChildren().clear();
            displayPane.getChildren().add(newPane);
            currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
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

    // Called on click of previous button
    public void onPreviousClicked() {

        // Fetching the previous batch only if offset is not 0 (Offset = 0 specifies first batch)
        if (offset > 0) {
            offset = offset - rowCount;
            loadItems();
        }
    }

}
