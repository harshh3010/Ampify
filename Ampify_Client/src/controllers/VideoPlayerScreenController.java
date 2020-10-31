package controllers;

import Services.MediaPlayerService;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Video;
import utilities.HomeScreenWidgets;
import utilities.UserApi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VideoPlayerScreenController implements Initializable {
    public JFXListView<Video> videoListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (UserApi.getInstance().getVideoPlayerDirectory() != null) {
            loadVideosInDirectory(UserApi.getInstance().getVideoPlayerDirectory());
        }
    }

    public void onChooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:/"));

        UserApi.getInstance().setVideoPlayerDirectory(directoryChooser.showDialog(videoListView.getScene().getWindow()));
        loadVideosInDirectory(UserApi.getInstance().getVideoPlayerDirectory());
    }

    private void loadVideosInDirectory(File selectedDirectory) {
        String[] contents = selectedDirectory.list();
        List<Video> videos = new ArrayList<>();

        HomeScreenWidgets.showProgressIndicator();

        new Thread(() -> {

            if (contents != null) {
                for (String fileName : contents) {
                    if (fileName.endsWith(".mp4") || fileName.endsWith(".mkv")) {
                        Video video = new Video();
                        video.setName(fileName);
                        video.setUrl(new File(UserApi.getInstance().getVideoPlayerDirectory() + "\\" + fileName).toURI().toString());
                        videos.add(video);
                    }
                }
            }

            Platform.runLater(() -> {
                HomeScreenWidgets.hideProgressIndicator();
                videoListView.setItems(FXCollections.observableArrayList(videos));

                videoListView.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {   // Play only when LMB is clicked
                        if (mouseEvent.getClickCount() == 2) {  // Play only in case of double click

                            Video video = videoListView.getSelectionModel().getSelectedItem();
                            Media media = new Media(video.getUrl());
                            MediaPlayer mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setAutoPlay(true);
                            VideoPlayer videoPlayer = new VideoPlayer(mediaPlayer);

                            Group root = new Group();
                            Scene scene = new Scene(root, 1280, 720);
                            scene.setRoot(videoPlayer);

                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();

                        }
                    }
                });

            });

        }).start();
    }
}
