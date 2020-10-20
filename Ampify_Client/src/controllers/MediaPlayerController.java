package controllers;

import Services.MediaPlayerService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MediaPlayerController implements Initializable {

    private MediaPlayer mediaPlayer;
    private Duration duration;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;

    @FXML
    public JFXButton playButton;
    @FXML
    public Label currentTimeLabel;
    @FXML
    public JFXSlider mediaPlayerSlider;
    public Label artistNameLabel;
    public Label songNameLabel;
    public Label totalTimeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (MediaPlayerService.currentSong != null) {
            Media media = new Media(MediaPlayerService.currentSong.getSongURL());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            songNameLabel.setText(MediaPlayerService.currentSong.getSongName());
            // TODO: DISPLAY CURRENT SONG INFO
        } else if (MediaPlayerService.previousSong != null) {
            // TODO: DISPLAY PREVIOUS SONG INFO
            songNameLabel.setText(MediaPlayerService.previousSong.getSongName());
        }

        mediaPlayerSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (mediaPlayerSlider.isValueChanging()) {
                    mediaPlayer.seek(duration.multiply(mediaPlayerSlider.getValue() / 100.0));
                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mediaPlayer.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mediaPlayer.pause();
                    stopRequested = false;
                } else {
                    playButton.setText("Pause");
                }
            }
        });

        mediaPlayer.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setText("Play");
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                updateValues();
            }
        });

        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText("Play");
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });
    }


    protected void updateValues() {
        if (currentTimeLabel != null && mediaPlayerSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
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
                }
            });
        }
    }

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

    public void onPlayButtonClicked(ActionEvent actionEvent) {
        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            // don't do anything in these states
            return;
        }

        if (status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.STOPPED) {
            // rewind the movie if we're sitting at the end
            if (atEndOfMedia) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                atEndOfMedia = false;
            }
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }
    }
}
