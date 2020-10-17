package services;

import model.Artist;
import model.Genres;
import model.Language;
import serverClasses.Main;
import serverClasses.requests.ChoicesFetchRequest;
import utilities.DatabaseConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AmpifyServices {

    public static ChoicesFetchRequest getUserChoices(ChoicesFetchRequest choicesFetchRequest) {
        ChoicesFetchRequest result = new ChoicesFetchRequest();

        List<Language> languageList = new ArrayList<>();
        List<Genres> genreList = new ArrayList<>();
        List<Artist> artistList = new ArrayList<>();

        // Fetching languages from database
        try {
            String query = "SELECT DISTINCT " + DatabaseConstants.USER_DETAILS_COL_LANGUAGE +
                    " FROM " + DatabaseConstants.USER_DETAILS_TABLE +
                    " WHERE " + DatabaseConstants.USER_DETAILS_COL_EMAIL + " = \"" + choicesFetchRequest.getEmail() + "\";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Language language = new Language();
                language.setLanguage(resultSet.getString(DatabaseConstants.USER_DETAILS_COL_LANGUAGE));
                languageList.add(language);
            }
            result.setLanguageList(languageList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fetching genres from database
        try {
            String query = "SELECT DISTINCT " + DatabaseConstants.USER_DETAILS_COL_GENRE +
                    " FROM " + DatabaseConstants.USER_DETAILS_TABLE +
                    " WHERE " + DatabaseConstants.USER_DETAILS_COL_EMAIL + " = \"" + choicesFetchRequest.getEmail() + "\";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genres genre = new Genres();
                genre.setGenres(resultSet.getString(DatabaseConstants.USER_DETAILS_COL_GENRE));
                genreList.add(genre);
            }
            result.setGenresList(genreList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fetching artists from database
        try {
            String query = "SELECT " +
                    "A." + DatabaseConstants.ARTIST_COL_ID + "," +
                    "A." + DatabaseConstants.ARTIST_COL_NAME + "," +
                    "A." + DatabaseConstants.ARTIST_COL_IMAGE + "," +
                    "A." + DatabaseConstants.ARTIST_COL_RATING + " " +
                    "FROM " + DatabaseConstants.ARTIST_TABLE + " A," + DatabaseConstants.USER_DETAILS_TABLE + " U " +
                    "WHERE U." + DatabaseConstants.USER_DETAILS_COL_ARTIST + " = A." + DatabaseConstants.ARTIST_COL_ID + " " +
                    "AND U." + DatabaseConstants.USER_DETAILS_COL_EMAIL + " = \"" + choicesFetchRequest.getEmail() + "\" " +
                    "GROUP BY " + DatabaseConstants.ARTIST_COL_ID + ";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setArtistID(resultSet.getInt(DatabaseConstants.ARTIST_COL_ID));
                artist.setArtistName(resultSet.getString(DatabaseConstants.ARTIST_COL_NAME));
                artist.setArtistImageURL(resultSet.getString(DatabaseConstants.ARTIST_COL_IMAGE));
                artist.setArtistRating(resultSet.getDouble(DatabaseConstants.ARTIST_COL_RATING));
                artistList.add(artist);
            }
            result.setArtistList(artistList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Artist artist : result.getArtistList()){
            System.out.println(artist.getArtistName());
        }

        return result;
    }

}
