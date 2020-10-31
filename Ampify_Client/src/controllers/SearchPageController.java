package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import model.Song;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchPageController {

    public JFXListView<Song> songsListView;
    private String queryText;
    private int offset, rowCount;

    public SearchPageController() {

        queryText = "";
        offset = 0;
        rowCount = 10;

        HomeScreenWidgets.searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            offset = 0;
            rowCount = 10;
            queryText = newValue.trim();
            loadItems();
        });
    }

    void loadItems() {

        // TODO: RESOLVE NOT ON FX THREAD ISSUE
        HomeScreenWidgets.showProgressIndicator();

        new Thread(() -> {

            List<Song> songList = new ArrayList<>();

            try {
                songList = AmpifyServices.getSearchResult(queryText, offset, rowCount);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            List<Song> finalSongList = songList;
            Platform.runLater(() -> {
                songsListView.setItems(FXCollections.observableArrayList(finalSongList));
                songsListView.setCellFactory(new SongCellFactory());
                HomeScreenWidgets.hideProgressIndicator();
            });

        }).start();

    }

    public void onPreviousClicked() {
        // Fetching the previous batch only if offset is not 0 (Offset = 0 specifies first batch)
        if (offset > 0) {
            offset = offset - rowCount;
            loadItems();
        }
    }

    public void onNextClicked() {
        // Fetching the next batch only if current one is non-empty (Empty specifies the end)
        if (!songsListView.getItems().isEmpty()) {
            offset = offset + rowCount;
            loadItems();
        }
    }
}
