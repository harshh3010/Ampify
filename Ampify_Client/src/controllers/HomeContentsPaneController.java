package controllers;

import CellFactories.ArtistCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import model.Artist;
import utilities.HomeScreenWidgets;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeContentsPaneController implements Initializable {
    public HBox musicCardHBox;
    public JFXListView<Artist> popularArtistsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Displaying top artists
        try {
            System.out.println("TOP ARTISTS: ");
            List<Artist> artists = AmpifyServices.getTopArtists();
            popularArtistsListView.setItems(FXCollections.observableArrayList(artists));
            popularArtistsListView.setCellFactory(new ArtistCellFactory(HomeScreenWidgets.displayPane));
            for (Artist artist : artists) {
                System.out.println(artist.getArtistName());
            }
            System.out.println();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
