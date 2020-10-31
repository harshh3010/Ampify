package controllers;

import CellFactories.SongCellFactory;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import model.Song;
import utilities.HomeScreenWidgets;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DownloadsScreenController implements Initializable {
    public JFXListView<Song> songListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Song> songs = new ArrayList<>();
        File directoryPath = new File("C:/Ampify-Player/Downloads/");

        HomeScreenWidgets.showProgressIndicator();

        new Thread(() -> {

            if (directoryPath.exists()) {

                String[] contents = directoryPath.list();

                if (contents != null) {
                    for (String fileName : contents) {
                        if (fileName.endsWith(".mp3") || fileName.endsWith(".wav")) {
                            Song song = new Song();
                            song.setSongName(fileName);
                            song.setAlbumID(0);
                            song.setArtistID(0);
                            song.setArtistName("UNKNOWN");
                            song.setSongID(0);
                            // TODO: ADD URL
                            song.setSongImageURL("");
                            song.setGenre("UNKNOWN");
                            song.setLanguage("UNKNOWN");
                            song.setReleaseDate("UNKNOWN");
                            song.setSongLyricsURL(null);
                            song.setSongRating(0);
                            song.setSongURL(new File("C:\\Ampify-Player\\Downloads\\" + fileName).toURI().toString());

                            songs.add(song);
                        }
                    }
                }
                songListView.setItems(FXCollections.observableArrayList(songs));
                songListView.setCellFactory(new SongCellFactory());

                Platform.runLater(HomeScreenWidgets::hideProgressIndicator);
            }

        }).start();
    }
}
