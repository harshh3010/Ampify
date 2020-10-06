package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public JFXButton signupBTN;
    @FXML
    private PasswordField passwordTF;

    public void onSignInClicked(ActionEvent actionEvent) {
        System.out.println("Login clicked!");
        passwordTF.setText("Hello");
    }

    public void onSignupClicked(ActionEvent actionEvent) throws IOException {

        // Scene to be displayed
        Parent registerScreenParent = FXMLLoader.load(getClass().getResource("../resources/register.fxml"));
        Scene registerScreenScene = new Scene(registerScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(registerScreenScene);
        window.show();
    }
}
