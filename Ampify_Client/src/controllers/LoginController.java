package controllers;

import Services.Hashing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

    public StackPane rootPane;
    public GridPane displayPane;
    public ProgressIndicator progressIndicator;
    volatile User check;
    @FXML
    private TextField emailTF, passwordTF;

    // This function will be called on clicking the sign in button
    public void onSignInClicked(ActionEvent actionEvent) {

        // Getting the email and password input by the user
        String email = emailTF.getText().trim();
        String password = passwordTF.getText();

        // Checking the validity of email and password
        if (!email.isEmpty() && !password.isEmpty()) {

            // Running a new thread to login the user if credentials are valid
            new Thread(() -> {

                progressIndicator.setVisible(true);
                displayPane.setDisable(true);

                try {
                    Socket socket = new Socket(Main.serverIp, Main.serverPort);

                    // Creating a new user auth object
                    UserAuth userAuth = new UserAuth(email, Hashing.hashPassword(password));

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
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "LOGGED IN SUCCESSFULLY!", ButtonType.OK);
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
                                    Main.userOutputStream.writeObject(choicesFetchRequest);
                                    Main.userOutputStream.flush();
                                    choicesFetchRequest = (ChoicesFetchRequest) Main.userInputStream.readObject();

                                    if (choicesFetchRequest != null
                                            && !choicesFetchRequest.getArtistList().isEmpty()
                                            && !choicesFetchRequest.getLanguageList().isEmpty()
                                            && !choicesFetchRequest.getGenresList().isEmpty()
                                    ) {

                                        // If choices present in database save them in UserApi class
                                        userApi.setLikedLanguages(choicesFetchRequest.getLanguageList());
                                        userApi.setLikedGenres(choicesFetchRequest.getGenresList());
                                        userApi.setLikedArtists(choicesFetchRequest.getArtistList());

                                        // Redirecting the user to home screen
                                        goToHomeScreen(actionEvent);

                                    } else {

                                        // If user's choices not found then redirect the user to choices screen
                                       goToDetailsScreen(actionEvent);

                                    }

                                } catch (Exception e) {
                                    System.out.println("Error establishing connection with the server!");
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                    }
                    // Displaying error in case of any failure during login
                    else if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.WRONG_CREDENTIALS))) {

                        Platform.runLater(() -> {
                            //DISPLAY ERROR
                            Alert alert = new Alert(Alert.AlertType.ERROR, "WRONG CREDENTIALS!", ButtonType.OK);
                            alert.showAndWait();
                        });
                    } else if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.NO_SUCH_USER))) {
                        Platform.runLater(() -> {
                            //DISPLAY ERROR
                            Alert alert = new Alert(Alert.AlertType.ERROR, "NO SUCH USER!", ButtonType.OK);
                            alert.showAndWait();
                        });
                    } else {
                        Platform.runLater(() -> {
                            //DISPLAY ERROR
                            Alert alert = new Alert(Alert.AlertType.ERROR, "ERROR OCCURRED!", ButtonType.OK);
                            alert.showAndWait();
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressIndicator.setVisible(false);
                progressIndicator.setDisable(true);
                displayPane.setDisable(false);

            }).start();

        } else {
            //DISPLAY ERROR DIALOG
            Alert alert = new Alert(Alert.AlertType.ERROR, "FIRST FILL ALL THE DETAILS!", ButtonType.OK);
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
        Parent homeScreen = FXMLLoader.load(getClass().getResource("/resources/fxml/home.fxml"));
        Scene homeScreenScene = new Scene(homeScreen);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(homeScreenScene);
        window.show();
    }
}
