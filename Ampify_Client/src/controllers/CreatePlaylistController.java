package controllers;

import Services.AmpifyServices;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Playlist;
import utilities.Status;
import utilities.UserApi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreatePlaylistController implements Initializable {

    public JFXTextField nameTextField;
    public JFXRadioButton groupRadioButton;
    public JFXRadioButton publicRadioButton;
    public JFXRadioButton personalRadioButton;
    public JFXRadioButton privateRadioButton;

    // Toggle groups for grouping the buttons
    private ToggleGroup privacyToggle;
    private ToggleGroup typeToggle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Creating button groups to ensure single selection
        privacyToggle = new ToggleGroup();
        typeToggle = new ToggleGroup();

        // Setting the type toggle in buttons
        groupRadioButton.setToggleGroup(typeToggle);
        personalRadioButton.setToggleGroup(typeToggle);

        // Setting the privacy toggle in buttons
        publicRadioButton.setToggleGroup(privacyToggle);
        privateRadioButton.setToggleGroup(privacyToggle);

        // Setting the default selected buttons
        publicRadioButton.setSelected(true);
        personalRadioButton.setSelected(true);

    }

    // Called on click of save button
    public void onSaveClicked() {

        // Declaring and initializing the playlist properties
        String privacy = "PUBLIC";
        String type = "GROUP";

        // Getting the selected radio buttons from 2 groups
        RadioButton privacyRB = (RadioButton) privacyToggle.getSelectedToggle();
        RadioButton typeRB = (RadioButton) typeToggle.getSelectedToggle();

        // Setting the  privacy property based on selected radio button
        if (privacyRB == publicRadioButton) {
            privacy = "PUBLIC";
        } else if (privacyRB == privateRadioButton) {
            privacy = "PRIVATE";
        }

        // Setting the type property based on selected radio button
        if (typeRB == groupRadioButton) {
            type = "GROUP";
        } else if (typeRB == personalRadioButton) {
            type = "PERSONAL";
        }

        // Creating a new playlist
        try {
            String result = AmpifyServices.createPlaylist(nameTextField.getText().trim(), type, privacy);
            if (result.equals(String.valueOf(Status.SUCCESS))) {

                // Closing the create playlist screen on success
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "SUCCESS", ButtonType.OK);
                alert.showAndWait();

                // Loading user's playlists
                try {
                    List<Playlist> playlists = AmpifyServices.getMyPlaylists();
                    List<Playlist> personalPlaylists = new ArrayList<>();
                    List<Playlist> groupPlaylists = new ArrayList<>();
                    for (Playlist playlist : playlists) {
                        if (playlist.getCategory().equals("GROUP")) {
                            groupPlaylists.add(playlist);
                        } else {
                            personalPlaylists.add(playlist);
                        }
                    }
                    UserApi.getInstance().setPersonalPlaylists(personalPlaylists);
                    UserApi.getInstance().setGroupPlaylist(groupPlaylists);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Stage stage = (Stage) nameTextField.getScene().getWindow();
                stage.close();

            } else {

                // Display error in case of failure
                System.out.println("Unable to create playlist!");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
