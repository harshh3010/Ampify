package controllers;

import Services.AmpifyServices;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.UserHistory;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    public TableView<UserHistory> historyTable;
    @FXML
    public TableColumn<UserHistory, String> songNameColumn;
    @FXML
    public TableColumn<UserHistory, Timestamp> timePlayedColumn;

    // Offset and row count to fetch a bunch of records from server
    private int offset, rowCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initially setting the offset to 0
        offset = 0;

        // Setting the rowCount to 10, i.e loading 10 items at a time
        rowCount = 10;

        // Loading the first batch
        loadItems();
    }

    // Called on click of next button
    public void onNextClicked() {
        // Fetching the next batch only if current one is non-empty (Empty specifies the end)
        if (!historyTable.getItems().isEmpty()) {
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
    // Function to load records from the server
    private void loadItems() {
        // Displaying the song history to the user
        try {
            List<UserHistory> userHistoryList = AmpifyServices.getUserHistory(offset, rowCount);
            historyTable.setItems(FXCollections.observableArrayList(userHistoryList));
            songNameColumn.setCellValueFactory(new PropertyValueFactory<>("songName"));
            timePlayedColumn.setCellValueFactory(new PropertyValueFactory<>("timePlayed"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
