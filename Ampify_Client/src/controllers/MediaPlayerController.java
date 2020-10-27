package controllers;

import CellFactories.SongsQueueCellFactory;
import Services.MediaPlayerService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Song;
import utilities.HomeScreenWidgets;

import java.net.URL;
import java.util.*;

public class MediaPlayerController implements Initializable {

    // UI Controls
    public JFXButton playButton;
    public Label currentTimeLabel;
    public JFXSlider mediaPlayerSlider;
    public Label songNameLabel;
    public Label totalTimeLabel;
    public JFXSlider volumeSlider;
    public Label artistNameLabel;

    // Media player implementation
    private MediaPlayer mediaPlayer;
    private Duration duration;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
        Initialize media player
        */
        if (!MediaPlayerService.currentPlaylist.isEmpty()) {

            // Get the url of first song in current playlist for playing
            Media media = new Media(MediaPlayerService.currentPlaylist.peekFirst().getSongURL());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);

            // Displaying the song info in UI
            assert MediaPlayerService.currentPlaylist.peekFirst() != null;
            songNameLabel.setText(MediaPlayerService.currentPlaylist.peekFirst().getSongName());
            assert MediaPlayerService.currentPlaylist.peekFirst() != null;
            artistNameLabel.setText(MediaPlayerService.currentPlaylist.peekFirst().getArtistName());

            // Displaying the songs in queue on home screen
            List<Song> list = new ArrayList<>(MediaPlayerService.currentPlaylist);
            HomeScreenWidgets.nowPlayingList.setItems(FXCollections.observableArrayList(list));
            HomeScreenWidgets.nowPlayingList.setCellFactory(new SongsQueueCellFactory());

        } else if (MediaPlayerService.previousSong != null) {

            // Get the url of previous song
            Media media = new Media(MediaPlayerService.previousSong.getSongURL());
            mediaPlayer = new MediaPlayer(media);

            // Not to play the song until user clicks play
            mediaPlayer.setAutoPlay(false);

            // Displaying the song info in UI
            songNameLabel.setText(MediaPlayerService.previousSong.getSongName());
            artistNameLabel.setText(MediaPlayerService.previousSong.getArtistName());

            // Displaying the songs in queue on home screen
            List<Song> list = new ArrayList<>(MediaPlayerService.currentPlaylist);
            HomeScreenWidgets.nowPlayingList.setItems(FXCollections.observableArrayList(list));
            HomeScreenWidgets.nowPlayingList.setCellFactory(new SongsQueueCellFactory());

        }

        // Adding the seek functionality using a slider
        mediaPlayerSlider.valueProperty().addListener(observable -> {
            if (mediaPlayerSlider.isValueChanging()) {
                mediaPlayer.seek(duration.multiply(mediaPlayerSlider.getValue() / 100.0));
            }
        });

        // Adding a listener to time to update the user of song progress
        mediaPlayer.currentTimeProperty().addListener(ov -> updateValues());

        // On Play
        mediaPlayer.setOnPlaying(() -> {
            if (stopRequested) {
                mediaPlayer.pause();
                stopRequested = false;
            } else {
                playButton.setText("Pause");
            }
        });

        // On Pause
        mediaPlayer.setOnPaused(() -> {
            System.out.println("onPaused");
            playButton.setText("Play");
        });

        // On loading media from url
        mediaPlayer.setOnReady(() -> {
            duration = mediaPlayer.getMedia().getDuration();
            updateValues();
        });

        volumeSlider.valueProperty().addListener(ov -> {
            if (volumeSlider.isValueChanging()) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
            }
        });

        // Function which will be called after end of media
        mediaPlayer.setOnEndOfMedia(() -> {

            if (MediaPlayerService.currentPlaylist.size() > 1) {

                // If there are more songs in the current playlist, then play them in loop
                Song song = MediaPlayerService.currentPlaylist.removeFirst();
                MediaPlayerService.currentPlaylist.addLast(song);
                MediaPlayerService.playSong(song);

            } else {

                // If there is only one song in current queue, then play it in loop
                assert MediaPlayerService.currentPlaylist.peekFirst() != null;
                MediaPlayerService.playSong(MediaPlayerService.currentPlaylist.peekFirst());

            }
        });
    }


    // Function to update the progress of media in the UI
    protected void updateValues() {
        if (currentTimeLabel != null && mediaPlayerSlider != null && volumeSlider != null) {
            Platform.runLater(() -> {
                Duration currentTime = mediaPlayer.getCurrentTime();
                currentTimeLabel.setText(formatTime(currentTime, duration));
                totalTimeLabel.setText(formatTotalTime(duration));
                mediaPlayerSlider.setDisable(duration.isUnknown());
                if (!mediaPlayerSlider.isDisabled()
                        && duration.greaterThan(Duration.ZERO)
                        && !mediaPlayerSlider.isValueChanging()) {
                    mediaPlayerSlider.setValue(currentTime.divide(duration).toMillis()
                            * 100.0);
                }
                if (!volumeSlider.isValueChanging()) {
                    volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume()
                            * 100));
                }
            });
        }
    }

    // Function to format the current time and display it in label
    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",
                        elapsedMinutes, elapsedSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

    // Function to format total duration and display it in label
    private String formatTotalTime(Duration duration) {
        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d",
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d",
                        durationMinutes,
                        durationSeconds);
            }
        }
        return "00 : 00";
    }

    // Adding the play/pause button functionality
    public void onPlayButtonClicked() {
        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            // don't do anything in these states
            return;
        }

        if (status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.STOPPED) {
            // rewind the media if we're sitting at the end
            if (atEndOfMedia) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                atEndOfMedia = false;
            }
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }
    }

    public void onNextClicked() {

        if (!MediaPlayerService.currentPlaylist.isEmpty()) {
            Song song = MediaPlayerService.currentPlaylist.removeFirst();
            MediaPlayerService.currentPlaylist.addLast(song);
            MediaPlayerService.playSong(MediaPlayerService.currentPlaylist.getFirst());
        }

    }

    public void onPrevClicked() {
        if (!MediaPlayerService.currentPlaylist.isEmpty()) {
            Song song = MediaPlayerService.currentPlaylist.removeLast();
            MediaPlayerService.currentPlaylist.addFirst(song);
            MediaPlayerService.playSong(MediaPlayerService.currentPlaylist.getFirst());
        }
    }

    public void onShuffleClick() {
        if (!MediaPlayerService.currentPlaylist.isEmpty()) {
            List<Song> songs = new ArrayList<>(MediaPlayerService.currentPlaylist);
            Collections.shuffle(songs);

            MediaPlayerService.currentPlaylist.clear();
            for (Song song : songs) {
                MediaPlayerService.currentPlaylist.addLast(song);
            }

            // Displaying the songs in queue on home screen
            List<Song> list = new ArrayList<>(MediaPlayerService.currentPlaylist);
            HomeScreenWidgets.nowPlayingList.setItems(FXCollections.observableArrayList(list));
            HomeScreenWidgets.nowPlayingList.setCellFactory(new SongsQueueCellFactory());
        }
    }
}
