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
import model.Artist;

import java.io.IOException;

public class ArtistCellController extends ListCell<Artist> {

    @FXML
    private Label nameLabel;
    @FXML
    private AnchorPane mainCard;

    // Display pane of home screen
    private Pane displayPane;

    public ArtistCellController(Pane displayPane) {
        this.displayPane = displayPane;
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/artistCard.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Artist artist, boolean b) {
        super.updateItem(artist, b);

        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            // Displaying the name of artist
            nameLabel.setText(artist.getArtistName());

            // Creating a background using image of artist
            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image(artist.getArtistImageURL()),
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true));

            // Setting the background in main artist card
            mainCard.setBackground(new Background(backgroundImage));

            // Adding mouse click listener to the artist card
            mainCard.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {   // Ensuring LMB is clicked
                        if (mouseEvent.getClickCount() == 2) {  // Ensuring its a double click
                            try {
                                // Move to artist screen on double click
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/artistScreen.fxml"));
                                Pane newLoadedPane = loader.load();
                                ArtistScreenController artistScreenController = loader.getController();
                                artistScreenController.saveArtistDetails(artist, displayPane);
                                displayPane.getChildren().remove(0);
                                displayPane.getChildren().add(newLoadedPane);

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
