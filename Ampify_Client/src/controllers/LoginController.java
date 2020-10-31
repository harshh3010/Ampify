package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClass.Main;
import model.User;
import model.UserAuth;
import serverClasses.requests.ChoicesFetchRequest;
import serverClasses.requests.LoginRequest;
import utilities.LoginStatus;
import utilities.UserApi;

import java.io.*;
import java.net.Socket;
import java.util.prefs.Preferences;

public class LoginController {

    volatile User check;
    @FXML
    private TextField emailTF, passwordTF;

    // This function will be called on clicking the sign in button
    public void onSignInClicked(ActionEvent actionEvent) {

        // Getting the email and password input by the user
        String email = emailTF.getText().trim();
        String pass = passwordTF.getText();

        // Checking the validity of email and password
        if (!email.isEmpty() && !pass.isEmpty()) {

            // Running a new thread to login the user if credentials are valid
            new Thread(() -> {

                try {
                    Socket socket = new Socket(Main.serverIp, Main.serverPort);

                    // Creating a new user auth object
                    UserAuth userAuth = new UserAuth(email, pass);

                    // Creating a login request from user auth object
                    LoginRequest loginrequest = new LoginRequest(userAuth);

                    // Sending the login request to server
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(loginrequest);
                    objectOutputStream.flush();

                    // Reading the response from the server
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    check = (User) objectInputStream.readObject();

                    // Checking the response received from the server
                    if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.SUCCESS))) {

                        // On successful login
                        Main.userSocket = socket;
                        Main.user = check;
                        Main.userInputStream = objectInputStream;
                        Main.userOutputStream = objectOutputStream;

                        Platform.runLater(() -> {

                            // DISPLAY SUCCESS DIALOG
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"LOGGED IN SUCCESSFULLY!", ButtonType.OK);
                            alert.showAndWait();

                            try {

                                // Saving login info in UserApi class
                                UserApi userApi = UserApi.getInstance();
                                userApi.setEmail(email);

                                // Saving login info in local storage
                                Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
                                preferences.put("isLoggedIn", "TRUE");
                                preferences.put("email", userApi.getEmail());

                                try {

                                    // Fetching user's choices from the database
                                    ChoicesFetchRequest choicesFetchRequest = new ChoicesFetchRequest(userApi.getEmail());
                                    objectOutputStream.writeObject(choicesFetchRequest);
                                    objectOutputStream.flush();
                                    choicesFetchRequest = (ChoicesFetchRequest) objectInputStream.readObject();

                                    if (choicesFetchRequest != null
                                            && !choicesFetchRequest.getArtistList().isEmpty()
                                            && !choicesFetchRequest.getLanguageList().isEmpty()
                                            && !choicesFetchRequest.getGenresList().isEmpty()
                                    ) {
                                        // On successfully loading saving user's choices in UserApi class
                                        userApi.setLikedLanguages(choicesFetchRequest.getLanguageList());
                                        userApi.setLikedGenres(choicesFetchRequest.getGenresList());
                                        userApi.setLikedArtists(choicesFetchRequest.getArtistList());

                                        // Redirecting the user to home screen
                                        goToHomeScreen(actionEvent);
                                    } else {

                                        // In case choices are not present, taking the user to choices screen
                                        goToDetailsScreen(actionEvent);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                    }
                    // Displaying error in case of any failure during login
                    else if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.WRONG_CREDENTIALS))) {

                        // TODO: DISPLAY ERROR

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //DISPLAY ERROR
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"WRONG CREDENTIALS!", ButtonType.OK);
                                alert.showAndWait();
                            }
                        });
                    } else if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.NO_SUCH_USER))) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //DISPLAY ERROR
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"NO SUCH USER!", ButtonType.OK);
                                alert.showAndWait();
                            }
                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //DISPLAY ERROR
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"ERROR OCCURRED!", ButtonType.OK);
                                alert.showAndWait();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } else {
            //DISPLAY ERROR DIALOG
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"FIRST FILL ALL THE DETAILS!", ButtonType.OK);
            alert.showAndWait();

        }
    }

    // This function will be called when user clicks signup button
    public void onSignupClicked(ActionEvent actionEvent) throws IOException {

        // Scene to be displayed
        Parent registerScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/register.fxml"));
        Scene registerScreenScene = new Scene(registerScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(registerScreenScene);
        window.show();
    }

    // Function to redirect the user to details screen
    private void goToDetailsScreen(ActionEvent actionEvent) throws IOException {
        // Scene to be displayed
        Parent detailsScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/choicesScreen.fxml"));
        Scene detailsScreenScene = new Scene(detailsScreenParent);

        // Getting the current stage window 
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(detailsScreenScene);
        window.show();
    }

    // Function to redirect the user to home screen
    private void goToHomeScreen(ActionEvent actionEvent) throws IOException {
        // Scene to be displayed
        Parent languageChoiceScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/home.fxml"));
        Scene languageChoiceScreenScene = new Scene(languageChoiceScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(languageChoiceScreenScene);
        window.show();
    }
}
