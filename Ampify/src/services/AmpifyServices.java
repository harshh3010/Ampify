package services;

import model.Artist;
import model.Genres;
import model.Language;
import model.User;
import serverClasses.Main;
import serverClasses.requests.*;
import utilities.DatabaseConstants;
import utilities.LoginStatus;
import utilities.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @noinspection ALL
 */
public class AmpifyServices {

    /*
    Function to register user
    */
    public static String registerUser(SignUpRequest signUpRequest) {
        String query = "INSERT INTO " + DatabaseConstants.AUTH_TABLE_NAME + "(" + DatabaseConstants.AUTH_TABLE_COL_EMAIL + "," + DatabaseConstants.AUTH_TABLE_COL_PASS + ") values(?,?);";
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.setString(1, signUpRequest.getUserAuth().getEmail());
            preparedStatement.setString(2, signUpRequest.getUserAuth().getPassword());
            preparedStatement.executeUpdate();
            return String.valueOf(Status.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }


    /*
    Function to login user
    */
    public static User userLogin(LoginRequest LoginRequest) {
        String query = "Select * from " + DatabaseConstants.AUTH_TABLE_NAME + " where " + DatabaseConstants.AUTH_TABLE_COL_EMAIL + "=?;";
        User user = new User();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.setString(1, LoginRequest.getUserAuth().getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(LoginRequest.getUserAuth().getPassword())) {
                    String query1 = "Select * from " + DatabaseConstants.AUTH_TABLE_NAME + " where " + DatabaseConstants.AUTH_TABLE_COL_EMAIL + "=?;";
                    PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
                    preparedStatement1.setString(1, resultSet.getString(1));
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while (resultSet1.next()) {
                        user.setUserLoginStatus((String.valueOf(LoginStatus.SUCCESS)));
                        System.out.println("Logged in successfuly");
                        return user;
                    }
                }
                user.setUserLoginStatus(String.valueOf(LoginStatus.WRONG_CREDENTIALS));
                System.out.println("Wrong credentials");
                return user;
            }
            user.setUserLoginStatus(String.valueOf(LoginStatus.NO_SUCH_USER));
            System.out.println("No such user");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setUserLoginStatus(String.valueOf(LoginStatus.SERVER_SIDE_ERROR));
        return user;
    }


    /*
    Function to get all artists from database
    */
    public static List<Artist> showAllArtists(ArtistFetchRequest artistFetchRequest) {
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


    /*
    Function to get all genres from database
     */
    public static List<Genres> showAllGenres(GenresFetchRequest genresRequest) {
        String query = "Select * from Genres;";
        List<Genres> genresList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Genres genresSet;
            while (resultSet.next()) {
                genresSet = new Genres();
                genresSet.setGenres(resultSet.getString(1));
                genresList.add(genresSet);
            }
            return genresList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genresList;
    }


    /*
    Function to get all languages from database
     */
    public static List<Language> showAllLanguages(LanguageFetchRequest languageRequest) {
        String query = "Select * from Language;";
        List<Language> languageList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Language langSet;
            while (resultSet.next()) {
                langSet = new Language();
                langSet.setLanguage(resultSet.getString(1));
                languageList.add(langSet);
            }
            return languageList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return languageList;
    }


    /*
    Function to save user choices for genres, languages, artists in the database
    */
    public static String saveChoices(SubmitChoicesRequest submitChoicesRequest) {

        // Saving user choices in the database
        try {
            // Query to insert data in user details table
            String query = "INSERT INTO " + DatabaseConstants.USER_DETAILS_TABLE +
                    "(" +
                    DatabaseConstants.USER_DETAILS_COL_EMAIL + "," +
                    DatabaseConstants.USER_DETAILS_COL_ARTIST + "," +
                    DatabaseConstants.USER_DETAILS_COL_LANGUAGE + "," +
                    DatabaseConstants.USER_DETAILS_COL_GENRE + "," +
                    DatabaseConstants.USER_DETAILS_COL_LIKED + "," +
                    DatabaseConstants.USER_DETAILS_COL_PLAYLIST +
                    ") VALUES(?,?,?,?,?,?);";

            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            // Looping through list to insert all records in database
            for (Language language : submitChoicesRequest.getSelectedLanguages()) {
                for (Genres genre : submitChoicesRequest.getSelectedGenres()) {
                    for (Artist artist : submitChoicesRequest.getSelectedArtists()) {

                        // Inserting values in prepared statement
                        preparedStatement.setString(1, submitChoicesRequest.getEmail());
                        preparedStatement.setInt(2, artist.getArtistID());
                        preparedStatement.setString(3, language.getLanguage());
                        preparedStatement.setString(4, genre.getGenres());
                        preparedStatement.setInt(5, 0);
                        preparedStatement.setInt(6, 0);

                        // Adding statement to batch of statements to be executed
                        preparedStatement.addBatch();
                    }
                }
            }

            try {
                // Executing the prepared batch of statements and returning success result
                preparedStatement.executeBatch();
                return String.valueOf(Status.SUCCESS);
            } catch (SQLException e) {
                // Displaying error in case of failure
                System.out.println("Error message: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(Status.FAILED);
    }


    /*
    Function to fetch user choices for artists, languages and genres from database
    */
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

        for (Artist artist : result.getArtistList()) {
            System.out.println(artist.getArtistName());
        }

        return result;
    }


    /*
    Function to get top artists from database
     */
    public static List<Artist> showTopArtists(ArtistFetchRequest artistFetchRequest) {

        /*
        SELECT a1.* FROM `artist` a1 INNER JOIN (SELECT MAX(artistRating) AS MaxRat FROM artist) a2 WHERE a1.artistRating = a2.MaxRat
         */

        String query = "SELECT a1.* " +
                "FROM " + DatabaseConstants.ARTIST_TABLE + " a1 " +
                "INNER JOIN (" +
                "SELECT MAX(" + DatabaseConstants.ARTIST_COL_RATING + ") " +
                "AS MaxRat FROM " + DatabaseConstants.ARTIST_TABLE + ") a2 " +
                "WHERE a1." + DatabaseConstants.ARTIST_COL_RATING + " = a2.MaxRat";
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
