/*
Controller class for song card
 */

package controllers;

import Services.AmpifyServices;
import Services.MediaPlayerService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Song;
import utilities.HomeScreenWidgets;
import utilities.Status;

import java.io.IOException;

public class SongCellController extends ListCell<Song> {

    @FXML
    private Label nameLabel;    // Label to display song name
    @FXML
    private Button menuButton;  // Button to open a popup menu

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
    private void setUpMenuButton() {

        // Creating the context menu
        ContextMenu contextMenu = new ContextMenu();

        // Creating items to be displayed in the menu
        MenuItem item1 = new MenuItem("Add to Favourites");
        MenuItem item2 = new MenuItem("Add to Playlist");
        MenuItem item3 = new MenuItem("Add to Queue");

        // Setting action events for menu items
        item1.setOnAction(actionEvent -> System.out.println("Add to favs"));
        item2.setOnAction(actionEvent -> System.out.println("Add to playlist"));
        item3.setOnAction(actionEvent -> System.out.println("Add to Queue"));

        // Adding the items in menu
        contextMenu.getItems().add(item1);
        contextMenu.getItems().add(item2);
        contextMenu.getItems().add(item3);

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

            // Setting up the popup menu
            setUpMenuButton();

            // Adding action event to play a song on double click
            this.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {   // Play only when LMB is clicked
                        if (mouseEvent.getClickCount() == 2) {  // Play only in case of double click

                            // Setting current song of media player to the selected song
                            MediaPlayerService.currentSong = song;

                            try {
                                // Loading the media player controls at the bottom of home screen
                                Pane mediaController = FXMLLoader.load(getClass().getResource("/resources/fxml/mediaPlayer.fxml"));
                                HomeScreenWidgets.bottomPane.getChildren().clear();
                                HomeScreenWidgets.bottomPane.getChildren().add(mediaController);

                                // Updating the history of user in database
                                String res = AmpifyServices.addSongToHistory(song.getSongID());
                                if (res.equals(String.valueOf(Status.SUCCESS))) {
                                    System.out.println("Song added to history");
                                } else {
                                    System.out.println("Song NOT added to history");
                                }

                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

}
