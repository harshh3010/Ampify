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
import model.Genres;
import model.Language;
import serverClasses.requests.GenresFetchRequest;
import serverClasses.requests.LanguageFetchRequest;

import javax.swing.*;
import java.net.*;
import java.util.List;
import java.util.ResourceBundle;
import  java.io.*;
public class GenresController implements Initializable {
    @FXML
    private TextField searchTF;
    private TableView<Language> tableLanguage;
    private TableColumn<Language , String> langHeading;
    private List<Genres> genresList;
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
                    GenresFetchRequest genresfetch= new GenresFetchRequest();
                    ObjectOutputStream oos = Main.userOutputStream;
                    oos.writeObject(genresfetch);
                    oos.flush();
                    ObjectInputStream ois = Main.userInputStream;
                    genresList = (List<Genres>) ois.readObject();
                    //listview=new ListView<>();


                    for(Genres l:genresList)
                    {

                        System.out.println(l.getGenres());
                    }
                    /*listview.getItems().addAll("hi","hjhj");
                    listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                    VBox layout=new VBox(10);
                    layout.setPadding(new Insets(20,20,20,20));
                    layout.getChildren().addAll(listview);

                    //tableLanguage.setItems((ObservableList<Language>) ois.readObject());*/

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



}

