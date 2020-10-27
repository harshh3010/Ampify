package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.Album;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;

import java.io.IOException;

public class AlbumCardController extends ListCell<Album> {

    @FXML
    private Label nameLabel;
    @FXML
    private AnchorPane mainCard;

    public AlbumCardController() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/albumCard.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void updateItem(Album album, boolean b) {
        super.updateItem(album, b);

        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            // Displaying the name of album
            nameLabel.setText(album.getAlbumName());

            mainCard.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {   // Ensuring LMB is clicked
                        if (mouseEvent.getClickCount() == 2) {  // Ensuring its a double click

                            try {
                                // Move to artist screen on double click
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/albumScreen.fxml"));
                                Pane newLoadedPane = loader.load();
                                AlbumScreenController albumScreenController = loader.getController();
                                albumScreenController.setAlbumDetails(album);
                                HomeScreenWidgets.displayPane.getChildren().remove(0);
                                HomeScreenWidgets.displayPane.getChildren().add(newLoadedPane);
                                HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.ALBUM_PAGE;
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
