package controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import mainClass.Main;
import model.Artist;
import model.Genres;
import model.Language;
import serverClasses.requests.ArtistFetchRequest;
import serverClasses.requests.GenresFetchRequest;
import serverClasses.requests.LanguageFetchRequest;
import serverClasses.requests.SubmitChoicesRequest;
import utilities.Status;
import utilities.UserApi;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChoiceController implements Initializable {

    @FXML
    private JFXListView<Language> languageList;
    @FXML
    private JFXListView<Genres> genreList;
    @FXML
    private JFXListView<Artist> artistList;
    private List<Language> languages;
    private List<Genres> genres;
    private List<Artist> artists;

    private ObjectOutputStream oos = Main.userOutputStream;
    private ObjectInputStream ois = Main.userInputStream;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        languageList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        genreList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        artistList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Fetching the languages from the sever
                try {
                    LanguageFetchRequest languageFetchRequest = new LanguageFetchRequest();
                    oos.writeObject(languageFetchRequest);
                    oos.flush();
                    languages = (List<Language>) ois.readObject();

                    ObservableList<Language> languagesToDisplay = FXCollections.observableArrayList(languages);
                    languageList.setItems(languagesToDisplay);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Fetching the genres from the sever
                try {
                    GenresFetchRequest genresFetchRequest = new GenresFetchRequest();
                    oos.writeObject(genresFetchRequest);
                    oos.flush();
                    genres = (List<Genres>) ois.readObject();

                    ObservableList<Genres> genresToDisplay = FXCollections.observableArrayList(genres);
                    genreList.setItems(genresToDisplay);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    ArtistFetchRequest artistsFetchRequest = new ArtistFetchRequest();
                    oos.writeObject(artistsFetchRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.userInputStream;
                    artists = (List<Artist>) ois.readObject();

                    ObservableList<Artist> artistsToDisplay = FXCollections.observableArrayList(artists);
                    artistList.setItems(artistsToDisplay);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    Called when user clicks continue button
     */
    public void onContinueClick(ActionEvent actionEvent) {

        // Obtain user's selected choices
        ObservableList<Language> list1 = languageList.getSelectionModel().getSelectedItems();
        ObservableList<Genres> list2 = genreList.getSelectionModel().getSelectedItems();
        ObservableList<Artist> list3 = artistList.getSelectionModel().getSelectedItems();

        // Converting observable list to array list before sending request to server
        List<Language> selectedLanguages = new ArrayList<>(list1);
        List<Genres> selectedGenres = new ArrayList<>(list2);
        List<Artist> selectedArtists = new ArrayList<>(list3);

        // Sending selected choices to the server
        try {
            UserApi userApi = UserApi.getInstance();
            SubmitChoicesRequest submitChoicesRequest = new SubmitChoicesRequest(
                    userApi.getEmail(),
                    selectedLanguages,
                    selectedGenres,
                    selectedArtists
            );
            oos.writeObject(submitChoicesRequest);
            oos.flush();
            String response = (String) ois.readObject();

            // Getting the response from server
            if (response.equals(String.valueOf(Status.SUCCESS))) {

                // Saving the choices in UserApi class on success
                userApi.setLikedArtists(selectedArtists);
                userApi.setLikedGenres(selectedGenres);
                userApi.setLikedLanguages(selectedLanguages);
                System.out.println("Saved choices");

            } else {
                // Failure when saving choices
                System.out.println("Error saving the choices");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
