package controllers;

import Services.AmpifyServices;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Playlist;
import utilities.Status;
import utilities.UserApi;

import java.io.IOException;

public class AddMemberScreenController {

    @FXML
    public JFXTextField usernameTF;

    // Playlist object to be fetched from previous screen
    private Playlist playlist;

    // Function to fetch the playlist object
    public void setPlaylistDetails(Playlist playlist) {
        this.playlist = playlist;
    }

    // Called when user clicks invite button
    public void onInviteClicked() {

        try {

            // Ensuring valid input
            if (!usernameTF.getText().trim().isEmpty()) {

                // TODO: ADD INVALID USERNAME CASE

                // Reading the response from the server after sending invite request
                String result = AmpifyServices.sendNotification(usernameTF.getText().trim(), playlist.getId());

                // Taking proper action based on response
                if (usernameTF.getText().trim().equals(UserApi.getInstance().getEmail())) {

                    System.out.println("Apne aap ko request bhej rha 🤦‍♂️");

                } else {
                    if (result.equals(String.valueOf(Status.SUCCESS))) {
                        System.out.println("SUCCESS");

                        Stage stage = (Stage) usernameTF.getScene().getWindow();
                        stage.close();

                    } else if (result.equals(String.valueOf(Status.ALREADY_EXIST))) {

                        System.out.println("Already sent");

                    } else {

                        System.out.println("Error sending request");

                    }
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
