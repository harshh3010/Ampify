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
import serverClasses.requests.LoginRequest;
import utilities.LoginStatus;
import utilities.Status;
import java.io.*;
import java.net.Socket;

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
                    try{
                        Socket socket = new Socket(Main.serverIp, Main.serverPort);
                        UserAuth userAuth = new UserAuth(email,pass);
                        LoginRequest loginrequest = new LoginRequest(userAuth);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(loginrequest);
                        oos.flush();
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        check = (User) ois.readObject();
                        if(check.getUserLoginStatus().equals(String.valueOf(LoginStatus.SUCCESS))){
                            Main.userSocket=socket;
                            Main.user=check;
                            Main.userInputStream=ois;
                            Main.userOutputStream=oos;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY Login SUCCESS
                                    System.out.println("Logged in successfully");
                                    /*try {
                                        // TODO: DISPLAY Userprofile page
                                        //root = FXMLLoader.load(getClass().getResource("/gui/studentportal.fxml"));
                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }*/


                                }
                            });

                        }else if(check.getUserLoginStatus().equals(String.valueOf(LoginStatus.WRONG_CREDENTIALS))) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY ERROR
                                    System.out.println("WRONG_CREDENTIALS");
                                }
                            });
                        }else if(check.getUserLoginStatus().equals(String.valueOf(LoginStatus.NO_SUCH_USER))) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY ERROR
                                    System.out.println("NO_SUCH_USER");
                                }
                            });
                        }else{
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO: DISPLAY ERROR
                                    System.out.println("error occurred");
                                }
                            });
                        }


                    }catch (Exception e){
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
}
