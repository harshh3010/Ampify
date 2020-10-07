package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.DatabaseConnection;
import java.io.IOException;
import java.sql.*;

public class RegisterController {

    // TODO: SHOW PROGRESS

    @FXML
    private TextField nameTF,emailTF,passTF,cnfpassTF;
    private String name;
    private String email;
    private String pass;

    public void onRegisterClick(ActionEvent actionEvent) {

        name = nameTF.getText().trim();
        email = emailTF.getText().trim();
        pass = passTF.getText();
        String cnfPass = cnfpassTF.getText();

        if(!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && pass.equals(cnfPass)){
            // No issues in filling up details
            // Register user
            registerUser(actionEvent);
        }else{
            if(!pass.equals(cnfPass)){
                // Passwords do not match
                Alert alert = new Alert(Alert.AlertType.ERROR, "The passwords do not match.", ButtonType.OK);
                alert.showAndWait();
            }else{
                // One of the fields is left empty
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill up all the fields.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void loginButtonClicked(ActionEvent actionEvent) throws IOException {
        goToLoginScreen(actionEvent);
    }

    private void registerUser(ActionEvent actionEvent){
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(DatabaseConnection.HOST, DatabaseConnection.DATABASE_USERNAME, DatabaseConnection.DATABASE_PASS);
            String query = "INSERT INTO user_auth(name,email,password) values('" + name + "','" + email + "','" + pass + "')";
            Statement sta = connection.createStatement();
            int x = sta.executeUpdate(query);

            if (x == 0) {
                // No rows inserted
                System.out.println("User exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR, "This email address is already in use.", ButtonType.OK);
                alert.showAndWait();
            } else {
                // Credentials saved in database
                System.out.println("User registered!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration Successful!", ButtonType.OK);
                alert.showAndWait();

                if(alert.getResult() == ButtonType.OK){
                    // Go to login screen
                    goToLoginScreen(actionEvent);
                }
            }
            connection.close();
        } catch (SQLException | IOException e) {
            if(e instanceof  SQLIntegrityConstraintViolationException){
                // Duplicate entry for email address
                Alert alert = new Alert(Alert.AlertType.ERROR, "This email address is already in use.", ButtonType.OK);
                alert.showAndWait();
            }else{
                // Any other sql exception
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while registering the user.", ButtonType.OK);
                alert.showAndWait();
            }

        }
    }

    private void goToLoginScreen(ActionEvent actionEvent) throws IOException {
        // Scene to be displayed
        Parent loginScreenParent = FXMLLoader.load(getClass().getResource("../resources/login.fxml"));
        Scene loginScreenScene = new Scene(loginScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(loginScreenScene);
        window.show();
    }

}
