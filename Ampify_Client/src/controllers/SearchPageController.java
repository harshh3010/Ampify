package controllers;

import CellFactories.SongCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import model.Song;
import utilities.HomeScreenWidgets;

import java.io.IOException;
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

        try {
            List<Song> songList = AmpifyServices.getSearchResult(queryText, offset,rowCount);
            songsListView.setItems(FXCollections.observableArrayList(songList));
            songsListView.setCellFactory(new SongCellFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        HomeScreenWidgets.hideProgressIndicator();
    }

    public void onPreviousClicked(ActionEvent actionEvent) {
        // Fetching the previous batch only if offset is not 0 (Offset = 0 specifies first batch)
        if (offset > 0) {
            offset = offset - rowCount;
            loadItems();
        }
    }

    public void onNextClicked(ActionEvent actionEvent) {
        // Fetching the next batch only if current one is non-empty (Empty specifies the end)
        if (!songsListView.getItems().isEmpty()) {
            offset = offset + rowCount;
            loadItems();
        }
    }
}
