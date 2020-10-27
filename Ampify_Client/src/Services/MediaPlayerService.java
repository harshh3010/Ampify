package Services;

import CellFactories.SongsQueueCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.Song;
import utilities.HomeScreenWidgets;
import utilities.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MediaPlayerService {

    public static Song previousSong;    // This will store the info of last song played by the user
    public static Deque<Song> currentPlaylist = new LinkedList<>(); // This will be used for creating a queue of songs

    // Function to play a song
    public static void playSong(Song song) {
        try {

            // Loading the media player controls at the bottom of home screen
            Pane mediaController = FXMLLoader.load(MediaPlayerService.class.getResource("/resources/fxml/mediaPlayer.fxml"));
            HomeScreenWidgets.bottomPane.getChildren().clear();
            HomeScreenWidgets.bottomPane.getChildren().add(mediaController);

            // Updating the history of user in database
            String res = AmpifyServices.addSongToHistory(song.getSongID());
            if (res.equals(String.valueOf(Status.SUCCESS))) {
                System.out.println("Song added to history");
            } else {
                System.out.println("Song NOT added to history");
            }

            // Displaying the songs in queue on home screen
            List<Song> list = new ArrayList<>(MediaPlayerService.currentPlaylist);
            HomeScreenWidgets.nowPlayingList.setItems(FXCollections.observableArrayList(list));
            HomeScreenWidgets.nowPlayingList.setCellFactory(new SongsQueueCellFactory());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
