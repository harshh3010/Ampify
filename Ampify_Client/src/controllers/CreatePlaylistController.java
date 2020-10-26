package controllers;

import Services.AmpifyServices;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import utilities.Status;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatePlaylistController implements Initializable {

    public JFXTextField nameTextField;
    public JFXRadioButton groupRadioButton;
    public JFXRadioButton publicRadioButton;
    public JFXRadioButton personalRadioButton;
    public JFXRadioButton privateRadioButton;

    private ToggleGroup privacyToggle;
    private ToggleGroup typeToggle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        privacyToggle = new ToggleGroup();
        typeToggle = new ToggleGroup();

        groupRadioButton.setToggleGroup(typeToggle);
        personalRadioButton.setToggleGroup(typeToggle);

        publicRadioButton.setToggleGroup(privacyToggle);
        privateRadioButton.setToggleGroup(privacyToggle);

        publicRadioButton.setSelected(true);
        personalRadioButton.setSelected(true);

    }

    public void onSaveClicked() {

        String privacy = "PUBLIC";
        String type = "GROUP";

        RadioButton privacyRB = (RadioButton) privacyToggle.getSelectedToggle();
        RadioButton typeRB = (RadioButton) typeToggle.getSelectedToggle();

        if (privacyRB == publicRadioButton) {
            privacy = "PUBLIC";
        } else if (privacyRB == privateRadioButton) {
            privacy = "PRIVATE";
        }

        if (typeRB == groupRadioButton) {
            type = "GROUP";
        } else if (typeRB == personalRadioButton) {
            type = "PERSONAL";
        }

        try {
            // TODO: DISPLAY DIALOG
            String result = AmpifyServices.createPlaylist(nameTextField.getText().trim(), type, privacy);
            if (result.equals(String.valueOf(Status.SUCCESS))) {
                System.out.println("Success!");
                Stage stage = (Stage) nameTextField.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Unable to create playlist!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
