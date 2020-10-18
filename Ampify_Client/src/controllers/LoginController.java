package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void onSignInClicked(ActionEvent actionEvent) {

        String email = emailTF.getText().trim();
        String pass = passwordTF.getText();
        if (!email.isEmpty() && !pass.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.print("jj");
                        Socket socket = new Socket(Main.serverIp, Main.serverPort);
                        UserAuth userAuth = new UserAuth(email, pass);
                        LoginRequest loginrequest = new LoginRequest(userAuth);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(loginrequest);
                        oos.flush();
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        check = (User) ois.readObject();
                        if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.SUCCESS))) {
                            Main.userSocket = socket;
                            Main.user = check;
                            Main.userInputStream = ois;
                            Main.userOutputStream = oos;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY Login SUCCESS
                                    System.out.println("Logged in successfully");
                                    try {
                                        // TODO: DISPLAY Userprofile page
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
                                            oos.writeObject(choicesFetchRequest);
                                            oos.flush();
                                            choicesFetchRequest = (ChoicesFetchRequest) ois.readObject();

                                            if (choicesFetchRequest != null
                                                    && !choicesFetchRequest.getArtistList().isEmpty()
                                                    && !choicesFetchRequest.getLanguageList().isEmpty()
                                                    && !choicesFetchRequest.getGenresList().isEmpty()
                                            ) {
                                                // Saving user's choices in UserApi class
                                                userApi.setLikedLanguages(choicesFetchRequest.getLanguageList());
                                                userApi.setLikedGenres(choicesFetchRequest.getGenresList());
                                                userApi.setLikedArtists(choicesFetchRequest.getArtistList());

                                                goToHomeScreen(actionEvent);
                                            } else {
                                                goToDetailsScreen(actionEvent);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.WRONG_CREDENTIALS))) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY ERROR
                                    System.out.println("WRONG_CREDENTIALS");
                                }
                            });
                        } else if (check.getUserLoginStatus().equals(String.valueOf(LoginStatus.NO_SUCH_USER))) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY ERROR
                                    System.out.println("NO_SUCH_USER");
                                }
                            });
                        } else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY ERROR
                                    System.out.println("error occurred");
                                }
                            });
                        }


                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }).start();
//

        } else {
            // TODO: DISPLAY ERROR DIALOG
            System.out.println("Fill details");
        }
    }

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

    private void goToDetailsScreen(ActionEvent actionEvent) throws IOException {
        // Scene to be displayed
        Parent languageChoiceScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/choicesScreen.fxml"));
        Scene languageChoiceScreenScene = new Scene(languageChoiceScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(languageChoiceScreenScene);
        window.show();
    }

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
