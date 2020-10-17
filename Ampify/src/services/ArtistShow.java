package services;

import model.Artist;
import model.Genres;
import model.Language;
import serverClasses.Main;
import serverClasses.requests.ArtistFetchRequest;
import serverClasses.requests.GenresFetchRequest;
import serverClasses.requests.LanguageFetchRequest;
import utilities.DatabaseConstants;

import java.awt.image.ImageObserver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Language;

public class ArtistShow {

    public List<Artist> artistshow(ArtistFetchRequest genresRequest) {
        String query = "Select * from artist;";
        List<Artist> artistList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Artist artistSet;
            while (resultSet.next()) {
                artistSet = new Artist();
                artistSet.setArtistID(resultSet.getInt(1));
                artistSet.setArtistName(resultSet.getString(2));
                artistSet.setArtistImageURL(resultSet.getString(3));
                artistSet.setArtistRating(resultSet.getDouble(4));

                artistList.add(artistSet);
            }
            return artistList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artistList;
    }

}