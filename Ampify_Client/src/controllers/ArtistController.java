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
import model.Artist;
import model.Genres;
import model.Language;
import serverClasses.requests.ArtistFetchRequest;
import serverClasses.requests.GenresFetchRequest;
import serverClasses.requests.LanguageFetchRequest;

import javax.swing.*;
import java.net.*;
import java.util.List;
import java.util.ResourceBundle;
import  java.io.*;
public class ArtistController implements Initializable {
    @FXML
    private TextField searchTF;
    private TableView<Language> tableLanguage;
    private TableColumn<Language , String> langHeading;


    private List<Artist> artistList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.userSocket;
                    ArtistFetchRequest artistfetch= new ArtistFetchRequest();
                    ObjectOutputStream oos = Main.userOutputStream;
                    oos.writeObject(artistfetch);
                    oos.flush();
                    ObjectInputStream ois = Main.userInputStream;
                    artistList = (List<Artist>) ois.readObject();
                    for(Artist l:artistList)
                    {

                        System.out.println(l.getArtistID()+" : "+l.getArtistName()+" rating :"+l.getArtistRating());
                    }
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

