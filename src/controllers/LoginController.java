package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.DatabaseConnection;

import  java.sql.*;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailTF,passwordTF;

    public void onSignInClicked(ActionEvent actionEvent) {

        String email = emailTF.getText().trim();
        String pass = passwordTF.getText();

        if(!email.isEmpty() && !pass.isEmpty()){
            try{
                Connection connection = DriverManager.getConnection(DatabaseConnection.HOST, DatabaseConnection.DATABASE_USERNAME, DatabaseConnection.DATABASE_PASS);
                String sql = "SELECT * FROM user_auth WHERE email = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    if(result.getString("password").equals(pass)){
                        // Login Success
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success!", ButtonType.OK);
                        alert.showAndWait();

                        if(alert.getResult() == ButtonType.OK){
                            System.out.println("Logged in");
                            // TODO: MOVE TO NEXT SCREEN
                            // TODO: SAVE LOGIN STATE
                        }
                    }else{
                        // Incorrect Password
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password!", ButtonType.OK);
                        alert.showAndWait();
                    }
                }else{
                     // User does not exist!
                    Alert alert = new Alert(Alert.AlertType.ERROR, "User not registered! Please register first.", ButtonType.OK);
                    alert.showAndWait();
                }

                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            // One of the fields is left empty
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill up all the fields.", ButtonType.OK);
            alert.showAndWait();
        }
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
