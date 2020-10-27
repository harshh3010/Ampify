package controllers;

import Services.AmpifyServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Playlist;
import utilities.*;

import java.io.IOException;

public class PlaylistCellController extends ListCell<Playlist> {

    @FXML
    private Text nameLabel;    // Label to display playlist name
    @FXML
    private Button menuButton;  // Button to open a popup menu

    public PlaylistCellController() {
        loadFXML();
    }

    /*
    Function to load the fxml file for displaying the playlist
    */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/playlistCell.fxml"));
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
    private void setUpMenuButton(Playlist playlist) {

        // Creating the context menu
        ContextMenu contextMenu = new ContextMenu();

        // Creating items to be displayed in the menu
        MenuItem item1 = new MenuItem("Delete Playlist");
        MenuItem item2;

        // Setting action events for menu items
        item1.setOnAction(actionEvent -> {
            // TODO: DISPLAY DIALOG, UPDATE UI
            try {

                // Reading the response from the server
                String result = AmpifyServices.deletePlaylist(playlist.getId());

                // Displaying proper message after reading the response
                if (result.equals(String.valueOf(Status.SUCCESS))) {
                    System.out.println("Playlist deleted!");
                } else if (result.equals(String.valueOf(Status.NOT_OWNER))) {
                    System.out.println("You do not have permission to delete this playlist!");
                } else {
                    System.out.println("An error occurred!");
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Adding the items in menu
        contextMenu.getItems().add(item1);

        // Adding item2 only in case of group playlists
        if (playlist.getCategory().equals("GROUP")) {
            item2 = new MenuItem("Invite User");
            item2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    if (playlist.getOwner().equals(UserApi.getInstance().getEmail())) {

                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/addMemberScreen.fxml"));
                            Parent parent = loader.load();
                            AddMemberScreenController addMemberScreenController = loader.getController();
                            addMemberScreenController.setPlaylistDetails(playlist);
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        // TODO: DISPLAY ERROR
                        System.out.println("YOU DO NOT HAVE PERMISSION TO PERFORM THIS ACTION");
                    }

                }
            });
            contextMenu.getItems().add(item2);
        }

        // Displaying the menu on button click
        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                contextMenu.show(menuButton, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        });
    }

    @Override
    protected void updateItem(Playlist playlist, boolean b) {
        super.updateItem(playlist, b);

        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            // Displaying the name of playlist
            nameLabel.setText(playlist.getPlaylistName());

            // Setting up menu for playlist options
            setUpMenuButton(playlist);

            // Setting mouse click listener on playlist cell
            this.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {   // Ensuring LMB is clicked
                        if (mouseEvent.getClickCount() == 2) {  // Ensuring its a double click

                            // Redirecting the user to playlist screen
                            try {

                                // Loading the playlist screen
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/playlistScreen.fxml"));
                                Parent parent = loader.load();

                                // Sending the playlist object to playlist screen
                                PlaylistScreenController playlistScreenController = loader.getController();
                                playlistScreenController.getPlaylistDetails(playlist);

                                // Displaying the playlist screen
                                HomeScreenWidgets.displayPane.getChildren().clear();
                                HomeScreenWidgets.displayPane.getChildren().add(parent);
                                HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.PLAYLIST_SCREEN;
                            } catch (IOException e) {
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
