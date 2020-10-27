package controllers;

import Services.AmpifyServices;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Playlist;
import utilities.Status;

import java.io.IOException;

public class AddMemberScreenController {

    @FXML
    public JFXTextField usernameTF;

    private Playlist playlist;

    public void setPlaylistDetails(Playlist playlist) {
        this.playlist = playlist;
    }

    public void onInviteClicked() {

        try {
            if (!usernameTF.getText().trim().isEmpty()) {

                // TODO: ADD INVALID USERNAME CASE

                String result = AmpifyServices.sendNotification(usernameTF.getText().trim(), playlist.getId());
                if (result.equals(String.valueOf(Status.SUCCESS))) {
                    System.out.println("SUCCESS");

                    Stage stage = (Stage) usernameTF.getScene().getWindow();
                    stage.close();

                } else if (result.equals(String.valueOf(Status.ALREADY_EXIST))) {

                    System.out.println("Already sent");

                } else {

                    System.out.println("Error sending request");

                }
            } else {
                // TODO: DISPLAY ERROR
                System.out.println("Username cannot be empty!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
