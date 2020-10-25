package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Song;
import serverClasses.requests.SongListType;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SongsListScreenController implements Initializable {

    @FXML
    public JFXListView<Song> songListView;
    @FXML
    public Label displayLabel;

    private SongListType songListType;

    // Offset and row count to fetch a bunch of records from server
    private int offset, rowCount;

    public void getFetchType(SongListType songListType) {
        this.songListType = songListType;

        // Initially setting the offset to 0
        offset = 0;

        // Setting the rowCount to 10, i.e loading 10 items at a time
        rowCount = 10;

        // Loading the first batch
        loadItems();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Function to load data from the server
    private void loadItems() {

        // Loading songs from the server
        try {
            List<Song> songs = new ArrayList<>();
            if (songListType == SongListType.RECENTLY_ADDED_SONGS) {
                songs = AmpifyServices.getRecentAddedSongs(offset, rowCount);
            } else if (songListType == SongListType.RECOMMENDED_SONGS) {
                songs = AmpifyServices.getUserChoiceSongs(offset, rowCount);
            }
            songListView.setItems(FXCollections.observableArrayList(songs));
            songListView.setCellFactory(new SongCellFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Called on click of back button
    public void onBackClicked() {

        // Redirecting the user to home page
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            HomeScreenWidgets.displayPane.getChildren().clear();
            HomeScreenWidgets.displayPane.getChildren().add(newPane);
            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
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
