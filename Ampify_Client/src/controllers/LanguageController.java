package controllers;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainClass.Main;
import model.Language;
import serverClasses.requests.LanguageFetchRequest;

import javax.swing.*;
import java.net.*;
import java.util.List;
import java.util.ResourceBundle;
import  java.io.*;
public class LanguageController implements Initializable {
    @FXML
    private TextField searchTF;
    private TableView<Language> tableLanguage;
    private TableColumn<Language , String> langHeading;
    private List<Language> langView;
    private ListView<String> listview;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("i");
        //langHeading.setCellValueFactory(new PropertyValueFactory<>("language"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.userSocket;
                    LanguageFetchRequest languagefetch= new LanguageFetchRequest();
                    ObjectOutputStream oos = Main.userOutputStream;
                    oos.writeObject(languagefetch);
                    oos.flush();
                    ObjectInputStream ois = Main.userInputStream;
                    langView = (List<Language>) ois.readObject();
                    listview=new ListView<>();


                    for(Language l:langView)
                    {

                        System.out.println(l.getLanguage());
                    }
                    listview.getItems().addAll("hi","hjhj");
                    listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                    VBox layout=new VBox(10);
                    layout.setPadding(new Insets(20,20,20,20));
                    layout.getChildren().addAll(listview);

                    //tableLanguage.setItems((ObservableList<Language>) ois.readObject());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });





                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();

    }
    public void onGenresClicked(ActionEvent actionEvent) throws IOException {

        // Scene to be displayed
        Parent registerScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/genresChoice.fxml"));
        Scene registerScreenScene = new Scene(registerScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(registerScreenScene);
        window.show();
    }
    public void onArtistClicked(ActionEvent actionEvent) throws IOException {

        // Scene to be displayed
        Parent registerScreenParent1 = FXMLLoader.load(getClass().getResource("/resources/fxml/artistChoice.fxml"));
        Scene registerScreenScene1 = new Scene(registerScreenParent1);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(registerScreenScene1);
        window.show();
    }


}

