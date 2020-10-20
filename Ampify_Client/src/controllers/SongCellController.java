package controllers;

import Services.MediaPlayerService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Song;
import utilities.HomeScreenWidgets;

import java.io.IOException;

public class SongCellController extends ListCell<Song> {

    @FXML
    private Label nameLabel;

    public SongCellController() {
        loadFXML();
    }

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

    @Override
    protected void updateItem(Song song, boolean b) {
        super.updateItem(song, b);


        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            nameLabel.setText(song.getSongName());
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            MediaPlayerService.currentSong = song;
                            try {
                                Pane mediaController = FXMLLoader.load(getClass().getResource("/resources/fxml/mediaPlayer.fxml"));
                                HomeScreenWidgets.bottomPane.getChildren().clear();
                                HomeScreenWidgets.bottomPane.getChildren().add(mediaController);
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
