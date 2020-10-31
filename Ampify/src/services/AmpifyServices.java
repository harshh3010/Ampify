package services;

import model.*;
import serverClasses.Main;
import serverClasses.requests.*;
import utilities.DatabaseConstants;
import utilities.LoginStatus;
import utilities.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

/**
 * @noinspection ALL
 */
public class AmpifyServices {

    /**
     * Function to register user
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

    /**
     * Function to login user
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
                        return user;
                    }
                }
                user.setUserLoginStatus(String.valueOf(LoginStatus.WRONG_CREDENTIALS));
                return user;
            }
            user.setUserLoginStatus(String.valueOf(LoginStatus.NO_SUCH_USER));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setUserLoginStatus(String.valueOf(LoginStatus.SERVER_SIDE_ERROR));
        return user;
    }

    /**
     * Function to get all artists from database
     */
    public static List<Artist> showAllArtists(ArtistFetchRequest artistFetchRequest) {
        String query = "Select * from "+DatabaseConstants.ARTIST_TABLE+";";
        //will return a list of all artists present in our DB
        List<Artist> artistList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Artist artistSet;
            //setting values to all the members of artist model
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

    /**
     * Function to get all genres from database
     */
    public static List<Genres> showAllGenres(GenresFetchRequest genresRequest) {
        String query = "Select * from "+DatabaseConstants.GENRES_TABLE+";";
        List<Genres> genresList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Genres genresSet;
            //setting values to all members of genres model
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

    /**
     * Function to get all languages from database
     */
    public static List<Language> showAllLanguages(LanguageFetchRequest languageRequest) {
        String query = "Select * from "+DatabaseConstants.LANGUAGE_TABLE+";";
        List<Language> languageList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Language langSet;
            //setting values to all the members of language model
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

    /**
     * Function to save user choices for genres, languages, artists in the database
     */
    public static String saveChoices(SubmitChoicesRequest submitChoicesRequest) {

        // Saving user choices in the database
        try {
            // Query to insert user's chosen languages in table
            String query1 = "INSERT INTO " + DatabaseConstants.USER_CHOICE_LANGUAGE_TABLE +
                    "(" +
                    DatabaseConstants.USER_CHOICE_LANGUAGE_COL_EMAIL + "," +
                    DatabaseConstants.USER_CHOICE_LANGUAGE_COL_NAME +
                    ") VALUES(?,?);";

            // Query to insert user's chosen genres in table
            String query2 = "INSERT INTO " + DatabaseConstants.USER_CHOICE_GENRES_TABLE +
                    "(" +
                    DatabaseConstants.USER_CHOICE_GENRES_COL_EMAIL + "," +
                    DatabaseConstants.USER_CHOICE_GENRES_COL_NAME +
                    ") VALUES(?,?);";

            // Query to insert user's chosen artists in table
            String query3 = "INSERT INTO " + DatabaseConstants.USER_CHOICE_ARTIST_TABLE +
                    "(" +
                    DatabaseConstants.USER_CHOICE_ARTIST_COL_EMAIL + "," +
                    DatabaseConstants.USER_CHOICE_ARTIST_COL_ARTIST_ID +
                    ") VALUES(?,?);";

            PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
            PreparedStatement preparedStatement2 = Main.connection.prepareStatement(query2);
            PreparedStatement preparedStatement3 = Main.connection.prepareStatement(query3);

            // Looping through lists to insert all records in database
            for (Language language : submitChoicesRequest.getSelectedLanguages()) {

                // Inserting values in prepared statement
                preparedStatement1.setString(1, submitChoicesRequest.getEmail());
                preparedStatement1.setString(2, language.getLanguage());

                // Adding statement to batch of statements to be executed
                preparedStatement1.addBatch();
            }
            for (Genres genre : submitChoicesRequest.getSelectedGenres()) {
                // Inserting values in prepared statement
                preparedStatement2.setString(1, submitChoicesRequest.getEmail());
                preparedStatement2.setString(2, genre.getGenres());
                // Adding statement to batch of statements to be executed
                preparedStatement2.addBatch();
            }
            for (Artist artist : submitChoicesRequest.getSelectedArtists()) {

                // Inserting values in prepared statement
                preparedStatement3.setString(1, submitChoicesRequest.getEmail());
                preparedStatement3.setInt(2, artist.getArtistID());

                // Adding statement to batch of statements to be executed
                preparedStatement3.addBatch();
            }
            try {

                // Executing the prepared batch of statements and returning success result
                preparedStatement1.executeBatch();
                preparedStatement2.executeBatch();
                preparedStatement3.executeBatch();

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
            String query = "SELECT DISTINCT " + DatabaseConstants.USER_CHOICE_LANGUAGE_COL_NAME +
                    " FROM " + DatabaseConstants.USER_CHOICE_LANGUAGE_TABLE +
                    " WHERE " + DatabaseConstants.USER_CHOICE_LANGUAGE_COL_EMAIL + " = \"" + choicesFetchRequest.getEmail() + "\";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Language language = new Language();
                language.setLanguage(resultSet.getString(DatabaseConstants.USER_CHOICE_LANGUAGE_COL_NAME));
                languageList.add(language);
            }
            result.setLanguageList(languageList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fetching genres from database
        try {
            String query = "SELECT DISTINCT " + DatabaseConstants.USER_CHOICE_GENRES_COL_NAME +
                    " FROM " + DatabaseConstants.USER_CHOICE_GENRES_TABLE +
                    " WHERE " + DatabaseConstants.USER_CHOICE_GENRES_COL_EMAIL + " = \"" + choicesFetchRequest.getEmail() + "\";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genres genre = new Genres();
                genre.setGenres(resultSet.getString(DatabaseConstants.USER_CHOICE_GENRES_COL_NAME));
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
                    "FROM " + DatabaseConstants.ARTIST_TABLE + " A," + DatabaseConstants.USER_CHOICE_ARTIST_TABLE + " U " +
                    "WHERE U." + DatabaseConstants.USER_CHOICE_ARTIST_COL_ARTIST_ID + " = A." + DatabaseConstants.ARTIST_COL_ID + " " +
                    "AND U." + DatabaseConstants.USER_CHOICE_ARTIST_COL_EMAIL + " = \"" + choicesFetchRequest.getEmail() + "\" " +
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

        return result;
    }

    /*
    Function to get top artists from database based on the rating!!
     */
    public static List<Artist> showTopArtists(ArtistFetchRequest artistFetchRequest) {

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
            //setting values to members of asrtist model
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
     * To return top albums to UI based on rating!!!
     * */
    public static List<Album> showTopAlbums(AlbumFetchRequest albumFetchRequest) {
        String query = "Select * " +
                "FROM " + DatabaseConstants.ALBUM_TABLE +
                " ORDER BY " + DatabaseConstants.ALBUM_COL_RATING + " DESC" +
                " LIMIT 0,4;";
        List<Album> topAlbumList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Album albumSet;
            //setting values to members of album model
            while (resultSet.next()) {
                albumSet = new Album();
                albumSet.setAlbumID(resultSet.getInt(1));
                albumSet.setAlbumName(resultSet.getString(2));
                albumSet.setReleaseDate(resultSet.getString(3));
                albumSet.setRating(resultSet.getDouble(4));
                //adding this song object to list of song type
                topAlbumList.add(albumSet);
            }
            return topAlbumList;//returning albumList
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return topAlbumList;
    }


    /**
     * To add the played song to history table and set is_playing to true
     */
    public static String addSongToHistory(AddToHistoryRequest addToHistoryRequest) {

        String query = "INSERT INTO " + DatabaseConstants.USER_HISTORY_TABLE +
                "(" + DatabaseConstants.USER_HISTORY_COL_EMAIL +
                "," + DatabaseConstants.USER_HISTORY_COL_SONGID +
                "," + DatabaseConstants.USER_HISTORY_COL_TIMEPLAYED +
                ") values(?,?,?);";
        //setting values in the db in the user History table
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.setString(1, addToHistoryRequest.getUserEmail());
            preparedStatement.setInt(2, addToHistoryRequest.getSongId());
            preparedStatement.setTimestamp(3, addToHistoryRequest.getTimestamp());
            preparedStatement.executeUpdate();
            return String.valueOf(Status.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }


    /**
     * this function is to add a song to favourite list of user
     * if song is already liked it will return a string stating ALREADY_LIKED
     *
     * @param addToFavouriteRequest
     * @return
     */
    public static String addSongToFavoutite(AddToFavouriteRequest addToFavouriteRequest) {
        /**
         * thru this query we first check if aready this song is present in his favourite list or not
         * *_* *_* *_* *_*
         */
        String query = " SELECT * FROM " + DatabaseConstants.FAVOURITE_SONG_TABLE +
                " WHERE " + DatabaseConstants.FAVOURITE_SONG_COL_SONGiD + "=\"" + addToFavouriteRequest.getSongID() + "\"" +
                " AND " + DatabaseConstants.FAVOURITE_SONG_COL_USEReMAIL + "=\"" + addToFavouriteRequest.getEmail() + "\"";
        try {
            PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next())
                return String.valueOf(Status.ALREADY_LIKED);
            else {
                query = "INSERT INTO " + DatabaseConstants.FAVOURITE_SONG_TABLE +
                        "(" + DatabaseConstants.FAVOURITE_SONG_COL_USEReMAIL +
                        "," + DatabaseConstants.FAVOURITE_SONG_COL_SONGiD +
                        ") values(?,?);";
                try {
                    PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
                    preparedStatement.setString(1, addToFavouriteRequest.getEmail());
                    preparedStatement.setInt(2, addToFavouriteRequest.getSongID());

                    preparedStatement.executeUpdate();
                    return String.valueOf(Status.SUCCESS);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return String.valueOf(Status.FAILED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }


    /**
     * function to return history of user
     * no of times a song played
     */
    public static List<UserHistory> showUserHistory(FetchUserHistoryRequest fetchUserHistoryRequest) {
        String email = fetchUserHistoryRequest.getEmail();
        int offset = fetchUserHistoryRequest.getOffset();
        int rowcount = fetchUserHistoryRequest.getRowcount();
        List<UserHistory> userHistoryList = new ArrayList<>();
        String query = "SELECT songs.IDsong,songs.songName,user_history.time_played" +
                " FROM songs" +
                " INNER JOIN user_history ON songs.IDsong=user_history.song_ID" +
                " WHERE user_history.user_email=\"" + email + "\" " +
                " ORDER BY user_history.time_played DESC " +
                " LIMIT " + offset + "," + rowcount + ";";
        UserHistory userHistory;

        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userHistory = new UserHistory();
                userHistory.setSongId(resultSet.getInt(1));
                userHistory.setSongName(resultSet.getString(2));
                userHistory.setTimePlayed(resultSet.getTimestamp(3));
                userHistoryList.add(userHistory);
            }
            return userHistoryList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }
        return userHistoryList;
    }



    /**
     * ths func perforns search on song name,artist name, artist name and fetch a song list matching with that string
     * offset, limit imp
     *
     * @param searchRequest
     * @return
     */
    public static List<Song> showSearchResults(SearchRequest searchRequest) {

        String query = "SELECT  artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "LEFT JOIN artist ON  songs.IDartist=artist.IDartist " +
                "LEFT JOIN albums ON  songs.IDalbum=albums.IDalbum " +
                " WHERE songs.songName LIKE \"" + searchRequest.getSearchText() + "%\" " +
                " OR artist.artistName LIKE \"" + searchRequest.getSearchText() + "%\" " +
                " OR albums.albumName LIKE \"" + searchRequest.getSearchText() + "%\" " +
                " GROUP BY songs.IDsong  " +
                " ORDER BY songs.IDsong DESC " +
                " LIMIT " + searchRequest.getOffset() + "," + searchRequest.getRowcount() + ";";

        Song songSet;
        List<Song> searchSongsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("OO");

            while (resultSet.next()) {
                songSet = new Song();

                songSet.setSongID(resultSet.getInt(DatabaseConstants.SONG_COL_ID));
                songSet.setSongName(resultSet.getString(DatabaseConstants.SONG_COL_NAME));
                songSet.setArtistID(resultSet.getInt(DatabaseConstants.SONG_COL_ARTISTID));
                songSet.setLanguage(resultSet.getString(DatabaseConstants.SONG_COL_LANGUAGE));
                songSet.setGenre(resultSet.getString(DatabaseConstants.SONG_COL_GENRES));
                songSet.setSongURL(resultSet.getString(DatabaseConstants.SONG_COL_MUSIC_URL));
                songSet.setSongLyricsURL(resultSet.getString(DatabaseConstants.SONG_COL_LYRICS_URL));
                songSet.setSongImageURL(resultSet.getString(DatabaseConstants.SONG_COL_IMAGE_URL));
                songSet.setAlbumID(resultSet.getInt(DatabaseConstants.SONG_COL_ALBUMID));
                songSet.setReleaseDate(resultSet.getString(DatabaseConstants.SONG_COL_RELEASE_DATE));
                songSet.setSongRating(resultSet.getDouble(DatabaseConstants.SONG_COL_RATING));
                songSet.setArtistName(resultSet.getString(DatabaseConstants.ARTIST_COL_NAME));

                searchSongsList.add(songSet);
            }
            return searchSongsList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }
        return searchSongsList;
    }

}