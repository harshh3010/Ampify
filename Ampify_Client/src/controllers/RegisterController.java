package controllers;

import javafx.application.Platform;
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
import mainClass.Main;
import model.UserAuth;
import serverClasses.requests.SignUpRequest;
import utilities.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RegisterController {

    @FXML
    private TextField emailTF, passTF, cnfpassTF;

    volatile String checkInput;

    public void onRegisterClick(ActionEvent actionEvent) {

        String email = emailTF.getText().trim();
        String pass = passTF.getText();
        String cnfPass = cnfpassTF.getText();

        if (!email.isEmpty() && !pass.isEmpty() && pass.equals(cnfPass)) {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(pass);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SignUpRequest signUpRequest = new SignUpRequest(userAuth);
                        Socket socket = new Socket(Main.serverIp, Main.serverPort);

                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(signUpRequest);
                        oos.flush();

                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        checkInput = (String) ois.readObject();

                        if(checkInput.equals(String.valueOf(Status.SUCCESS))){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    //  DISPLAY REGISTRATION SUCCESS
                                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"REGISTRATION SUCCESS!", ButtonType.OK);
                                    alert.showAndWait();
                                    try {
                                        goToLoginScreen(actionEvent);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else{
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY ERROR
                                    Alert alert=new Alert(Alert.AlertType.ERROR,"ERROR IN REGISTRATION!", ButtonType.OK);
                                    alert.showAndWait();
                                }
                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            // DISPLAY ERROR DIALOG
            Alert alert=new Alert(Alert.AlertType.ERROR,"ERROR!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void loginButtonClicked(ActionEvent actionEvent) throws IOException {
        goToLoginScreen(actionEvent);
    }

    private void goToLoginScreen(ActionEvent actionEvent) throws IOException {
        // Scene to be displayed
        Parent loginScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
        Scene loginScreenScene = new Scene(loginScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(loginScreenScene);
        window.show();
    }

}
