package controllers;

import CellFactories.SongCellFactory;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import model.Song;

import java.io.File;
import java.net.URI;
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

        if (directoryPath.exists()) {

            String[] contents = directoryPath.list();

            for(String fileName : contents){
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
                // TODO: ADD URL
                song.setSongLyricsURL("");
                song.setSongRating(0);
                song.setSongURL(new File("C:\\Ampify-Player\\Downloads\\" + fileName).toURI().toString());

                songs.add(song);
            }


            songListView.setItems(FXCollections.observableArrayList(songs));
            songListView.setCellFactory(new SongCellFactory());

        }
    }
}
