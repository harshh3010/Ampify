/*
Controller class for song card
*/

package controllers;

import CellFactories.SongsQueueCellFactory;
import Services.AmpifyServices;
import Services.DownloadService;
import Services.MediaPlayerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Song;
import utilities.HomeScreenWidgets;
import utilities.Status;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongCellController extends ListCell<Song> {

    @FXML
    private Label nameLabel;    // Label to display song name
    @FXML
    private Button menuButton;  // Button to open a popup menu
    @FXML
    private Label artistNameLabel;  // Label to display artist's name

    public SongCellController() {
        loadFXML();
    }

    /*
    Function to load the fxml file for displaying the song
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/songCard.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Function to setup the menu to be displayed on click of menuButton
     */
    private void setUpMenuButton(Song song) {

        // Creating the context menu
        ContextMenu contextMenu = new ContextMenu();

        // Creating items to be displayed in the menu
        MenuItem item1 = new MenuItem("Add to Favourites");
        MenuItem item2 = new MenuItem("Add to Playlist");
        MenuItem item3 = new MenuItem("Add to Queue");
        MenuItem item4 = new MenuItem("Download");

        // Setting action events for menu items
        item1.setOnAction(actionEvent -> {
            try {

                String result = AmpifyServices.addToFavorites(song.getSongID());

                if (result.equals(String.valueOf(Status.ALREADY_LIKED))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "ALREADY PRESENT IN FAVOURITES!", ButtonType.OK);
                    alert.showAndWait();
                } else if (result.equals(String.valueOf(Status.SUCCESS))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "ADDED TO FAVOURITES!", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "UNABLE TO ADD TO FAVOURITES!", ButtonType.OK);
                    alert.showAndWait();
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // On clicking add to playlist button
        item2.setOnAction(actionEvent -> {
            try {

                // Redirecting the user to add to playlist screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/addSongToPlaylistScreen.fxml"));
                Parent addToPlaylistScreen = loader.load();

                // Passing the song object to the add to playlist screen
                AddToPlaylistController addToPlaylistController = loader.getController();
                addToPlaylistController.getSongToAdd(song);

                // Displaying the add to playlist screen in new window
                Stage stage = new Stage();
                stage.setScene(new Scene(addToPlaylistScreen));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        // On clicking add to queue button
        item3.setOnAction(actionEvent -> {
            MediaPlayerService.currentPlaylist.addLast(song);

            // Displaying the songs in queue on home screen
            List<Song> list = new ArrayList<>(MediaPlayerService.currentPlaylist);
            HomeScreenWidgets.nowPlayingList.setItems(FXCollections.observableArrayList(list));
            HomeScreenWidgets.nowPlayingList.setCellFactory(new SongsQueueCellFactory());
        });

        // On clicking the download button
        item4.setOnAction(actionEvent -> {

            String filePath = "C:/Ampify-Player/Downloads/";
            File file = new File(filePath);
            boolean fileMade = false;
            if (!file.exists()) {
                fileMade = file.mkdirs();
            }

            if (fileMade) {
                File outputFile = new File(filePath + song.getSongName().replaceAll("\\s", "") + "-" + song.getArtistName().replaceAll("\\s", "") + ".mp3");
                new Thread(new DownloadService(song.getSongURL(), outputFile)).start();
            } else {
                System.out.println("An error occurred");
            }

        });

        // Adding the items in menu (Song ID = 0 ===> Downloaded/Local music)
        if (song.getSongID() != 0) {
            contextMenu.getItems().add(item1);
            contextMenu.getItems().add(item2);
            contextMenu.getItems().add(item4);
        }

        contextMenu.getItems().add(item3);

        contextMenu.setStyle("-fx-text-fill: black;");

        // Displaying the menu on button click
        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                contextMenu.show(menuButton, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        });

    }

    @Override
    protected void updateItem(Song song, boolean b) {
        super.updateItem(song, b);
        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            // Displaying the name of song
            nameLabel.setText(song.getSongName());

            // Displaying artist's name
            artistNameLabel.setText(song.getArtistName());

            // Setting up the popup menu
            setUpMenuButton(song);

            // Adding action event to play a song on double click
            this.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {   // Play only when LMB is clicked
                    if (mouseEvent.getClickCount() == 2) {  // Play only in case of double click

                        // Setting current song of media player to the selected song
                        MediaPlayerService.currentPlaylist.clear();
                        MediaPlayerService.currentPlaylist.addLast(song);

                        MediaPlayerService.playSong(song);

                    }
                }
            });
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

}
