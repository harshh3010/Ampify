package controllers;

import Services.AmpifyServices;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"BEING AN OWNER YOU ALREADY OWN THE MEMBERSHIP", ButtonType.OK);
                    alert.showAndWait();

                } else {
                    if (result.equals(String.valueOf(Status.SUCCESS))) {
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"SUCCESS!", ButtonType.OK);
                        alert.showAndWait();
                        Stage stage = (Stage) usernameTF.getScene().getWindow();
                        stage.close();

                    } else if (result.equals(String.valueOf(Status.ALREADY_EXIST))) {

                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"ALREADY SENT!", ButtonType.OK);
                        alert.showAndWait();

                    } else {
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"ERROR SENDING REQUEST!", ButtonType.OK);
                        alert.showAndWait();

                    }
                }

            } else {
                // DISPLAY ERROR
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"USERNAME CANNOT BE EMPTY!", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
