package controllers;

import CellFactories.SongCellFactory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import model.Song;
import utilities.HomeScreenWidgets;
import utilities.UserApi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LocalMusicScreenController implements Initializable {

    @FXML
    public JFXButton chooseDirectoryButton;
    public JFXListView<Song> songsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(UserApi.getInstance().getSelectedDirectory() != null){
            loadMusicInDirectory(UserApi.getInstance().getSelectedDirectory());
        }

    }

    public void onChooseClick() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:/"));

        UserApi.getInstance().setSelectedDirectory(directoryChooser.showDialog(chooseDirectoryButton.getScene().getWindow()));
        loadMusicInDirectory(UserApi.getInstance().getSelectedDirectory());
    }

    private void loadMusicInDirectory(File selectedDirectory){
        String[] contents = selectedDirectory.list();
        List<Song> songs = new ArrayList<>();

        HomeScreenWidgets.showProgressIndicator();

        new Thread(() -> {

            if(contents != null){
                for(String fileName : contents){
                    if(fileName.endsWith(".mp3") || fileName.endsWith(".wav")){
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

            songsListView.setItems(FXCollections.observableArrayList(songs));
            songsListView.setCellFactory(new SongCellFactory());

            Platform.runLater(HomeScreenWidgets::hideProgressIndicator);

        }).start();
    }

}
