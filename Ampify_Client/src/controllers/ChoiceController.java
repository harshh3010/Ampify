package controllers;

import com.jfoenix.controls.JFXListView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import mainClass.Main;
import model.Genres;
import model.Language;
import serverClasses.requests.GenresFetchRequest;
import serverClasses.requests.LanguageFetchRequest;
import serverClasses.requests.SubmitChoicesRequest;
import utilities.Status;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChoiceController implements Initializable {

    @FXML
    private JFXListView<String> languageList;
    @FXML
    private JFXListView<String> genreList;
    @FXML
    private JFXListView artistList;
    private List<Language> languages;
    private List<Genres> genres;

    private ObjectOutputStream oos = Main.userOutputStream;
    private ObjectInputStream ois = Main.userInputStream;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        languageList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        genreList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Fetching the languages from the sever
                try {
                    LanguageFetchRequest languageFetchRequest = new LanguageFetchRequest();
                    oos.writeObject(languageFetchRequest);
                    oos.flush();
                    languages = (List<Language>) ois.readObject();
                    List<String> result = new ArrayList<>();

                    for (Language l : languages) {
                        result.add(l.getLanguage());
                    }

                    ObservableList<String> languagesToDisplay = FXCollections.observableArrayList(result);
                    languageList.setItems(languagesToDisplay);

                } catch (Exception e) {
                    System.out.println(e);
                }

                // Fetching the genres from the sever
                try{
                    GenresFetchRequest genresFetchRequest= new GenresFetchRequest();
                    oos.writeObject(genresFetchRequest);
                    oos.flush();
                    genres = (List<Genres>) ois.readObject();
                    List<String> result = new ArrayList<>();

                    for(Genres g: genres)
                    {
                        result.add(g.getGenres());
                    }

                    ObservableList<String> genresToDisplay = FXCollections.observableArrayList(result);
                    genreList.setItems(genresToDisplay);

                }catch (Exception e){
                    System.out.println(e);
                }

            }
        }).start();
    }

    /*
    Called when user clicks continue button
     */
    public void onContinueClick(ActionEvent actionEvent) {
        List<String> selectedLanguages = languageList.getSelectionModel().getSelectedItems();
        List<String> selectedGenres = genreList.getSelectionModel().getSelectedItems();

        // Sending selected choices to the server
        try{
            SubmitChoicesRequest submitChoicesRequest = new SubmitChoicesRequest(
                    selectedLanguages,
                    selectedGenres,
                    selectedGenres  // TODO: CHANGE TO ARTISTS
            );
            oos.writeObject(submitChoicesRequest);
            oos.flush();

            String response = (String) ois.readObject();
            if (response.equals(Status.SUCCESS)){
                System.out.println("Saved choices");
            }else{
                System.out.println("Error saving the choices");
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
