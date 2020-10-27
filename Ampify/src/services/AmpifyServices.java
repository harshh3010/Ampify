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


    /**
     * Function to get all artists from database
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


    /**
     * Function to get all genres from database
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


    /**
     * Function to get all languages from database
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


    /*
     * To return top albums to UI!!!
     * */
    public static List<Album> showTopAlbums(AlbumFetchRequest albumFetchRequest) {
        String query = "Select * " +
                "FROM " + DatabaseConstants.ALBUM_TABLE +
                " ORDER BY " + DatabaseConstants.ALBUM_COL_RATING + " DESC;";
        List<Album> topAlbumList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Album albumSet;
            while (resultSet.next()) {
                albumSet = new Album();
                albumSet.setAlbumID(resultSet.getInt(1));
                albumSet.setAlbumName(resultSet.getString(2));
                albumSet.setReleaseDate(resultSet.getString(3));
                albumSet.setRating(resultSet.getDouble(4));
                //adding this song object to list of song type
                topAlbumList.add(albumSet);
            }
            return topAlbumList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return topAlbumList;
    }


    /*
     * To return top songs to UI!!!
     * */
    public static List<Song> showTopSongs(SongFetchRequest songFetchRequest) {
        int offset = songFetchRequest.getOffset();
        int rowcount = songFetchRequest.getRowcount();
        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM artist " +
                "INNER JOIN songs ON artist.IDartist = songs.IDartist " +
                " ORDER BY songs.IDsong  " +
                "LIMIT " + offset + " , " + rowcount + ";";


        List<Song> topSongList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Song songSet;

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

                //adding this song object to list of song type
                topSongList.add(songSet);
            }
            return topSongList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return topSongList;
    }


    /*
     * To return songs of particular artist to UI!!!
     * */
    public static List<Song> showSongsOfParticularArtist(SongFetchRequest songFetchRequest) {
        int offset = songFetchRequest.getOffset();
        int artistID = songFetchRequest.getID();
        int rowcount = songFetchRequest.getRowcount();
        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM artist " +
                "INNER JOIN songs ON artist.IDartist = songs.IDartist " +
                "WHERE artist.IDartist =\"" + artistID + "\" " +
                "LIMIT " + offset + " , " + rowcount + ";";


        List<Song> songListOfArtist = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Song songSet;

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
                System.out.println(resultSet.getString(DatabaseConstants.ARTIST_COL_NAME));
                //adding this song object to list of song type
                songListOfArtist.add(songSet);
            }
            return songListOfArtist;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return songListOfArtist;
    }


    /*
     * To return  songs of aprticular album to UI!!!
     * */
    public static List<Song> showSongsOfParticularAlbum(SongFetchRequest songFetchRequest) {

        int albumID = songFetchRequest.getID();
        int offset = songFetchRequest.getOffset();
        int rowcount = songFetchRequest.getRowcount();
        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM artist " +
                "INNER JOIN songs ON artist.IDartist = songs.IDartist " +
                "WHERE artist.IDartist =\"" + albumID + "\" " +
                "LIMIT " + offset + " , " + rowcount + ";";


        List<Song> songListOfAlbum = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Song songSet;

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
                System.out.println(resultSet.getString(DatabaseConstants.ARTIST_COL_NAME));
                //adding this song object to list of song type
                songListOfAlbum.add(songSet);
            }
            return songListOfAlbum;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return songListOfAlbum;
    }

    /**
     * To add the played song to history table and set is_playing to true
     */
    public static String playSongAddHistory(AddToHistoryRequest addToHistoryRequest) {

        String query = "INSERT INTO " + DatabaseConstants.USER_HISTORY_TABLE +
                "(" + DatabaseConstants.USER_HISTORY_COL_EMAIL +
                "," + DatabaseConstants.USER_HISTORY_COL_SONGID +
                "," + DatabaseConstants.USER_HISTORY_COL_TIMEPLAYED +
                ") values(?,?,?);";
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
     * fetching user choice songs
     * * ordered by song ID ascending order
     * * rows fetched = rowcount
     */


    public static List<Song> showSongsOfUserChoice(SongFetchRequest songFetchRequest) {

        String languagest = "";
        String genrest = "";
        String artistst = "";
        int offset = songFetchRequest.getOffset();
        int rowcount = songFetchRequest.getRowcount();
        List<Song> userchoiceSong = new ArrayList<>();

        /**
         * Fetching user preferred languages from database
         */
        try {
            String query = "SELECT DISTINCT user_language " + DatabaseConstants.USER_CHOICE_LANGUAGE_COL_NAME +
                    " FROM " + DatabaseConstants.USER_CHOICE_LANGUAGE_TABLE +
                    " WHERE " + DatabaseConstants.USER_CHOICE_LANGUAGE_COL_EMAIL + " = \"" + songFetchRequest.getEmail() + "\";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                languagest += "\"" + resultSet.getString(DatabaseConstants.USER_CHOICE_LANGUAGE_COL_NAME) + "\" , ";
            }
            if (languagest.length() > 2)
                languagest = languagest.substring(0, languagest.length() - 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * Fetching user preferred genres from database
         */

        try {
            String query = "SELECT DISTINCT " + DatabaseConstants.USER_CHOICE_GENRES_COL_NAME +
                    " FROM " + DatabaseConstants.USER_CHOICE_GENRES_TABLE +
                    " WHERE " + DatabaseConstants.USER_CHOICE_GENRES_COL_EMAIL + " = \"" + songFetchRequest.getEmail() +
                    "\" ;";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                genrest += "\"" + resultSet.getString(DatabaseConstants.USER_CHOICE_GENRES_COL_NAME) + "\" , ";
            }
            if (genrest.length() > 2)
                genrest = genrest.substring(0, genrest.length() - 2);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fetching user preferred artists from database
        try {
            String query = "SELECT " +
                    "A." + DatabaseConstants.ARTIST_COL_ID + "," +
                    "A." + DatabaseConstants.ARTIST_COL_NAME + "," +
                    "A." + DatabaseConstants.ARTIST_COL_IMAGE + "," +
                    "A." + DatabaseConstants.ARTIST_COL_RATING + " " +
                    "FROM " + DatabaseConstants.ARTIST_TABLE + " A," + DatabaseConstants.USER_CHOICE_ARTIST_TABLE + " U " +
                    "WHERE U." + DatabaseConstants.USER_CHOICE_ARTIST_COL_ARTIST_ID + " = A." + DatabaseConstants.ARTIST_COL_ID + " " +
                    "AND U." + DatabaseConstants.USER_CHOICE_ARTIST_COL_EMAIL + " = \"" + songFetchRequest.getEmail() + "\" " +
                    "GROUP BY " + DatabaseConstants.ARTIST_COL_ID + ";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                artistst += "\"" + Integer.toString(id) + "\" , ";
            }
            if (artistst.length() > 2)
                artistst = artistst.substring(0, artistst.length() - 2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(languagest);
        System.out.println(genrest);
        System.out.println(artistst);
        //TODO USE INNER JOIN ;THIS IS SOMETHING NOT TO BE DONE BUT TAKEN LITE FOR NOW!
        // :(
        try {

            String query2 = "SELECT DISTINCT * FROM " + DatabaseConstants.SONG_TABLE +
                    " WHERE " + DatabaseConstants.SONG_COL_LANGUAGE + " IN  (" + languagest +
                    ") OR " + DatabaseConstants.SONG_COL_GENRES + " IN (" + genrest +
                    ") OR " + DatabaseConstants.SONG_COL_ARTISTID + " IN (" + artistst + ") " +
                    " ORDER BY songs.IDsong  " +
                    "LIMIT " + offset + " , " + rowcount + ";";
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query2);
            ResultSet resultSet = preparedStatement.executeQuery();
            Song songSet;
            String query1;
            while (resultSet.next()) {
                songSet = new Song();
                query1 = "SELECT * FROM " + DatabaseConstants.ARTIST_TABLE + " WHERE " + DatabaseConstants.ARTIST_COL_ID + " =" + resultSet.getInt(3) + ";";
                PreparedStatement preparedStatement2 = Main.connection.prepareStatement(query1);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    songSet.setArtistName(resultSet2.getString(2));
                }
                songSet.setSongID(resultSet.getInt(1));
                songSet.setSongName(resultSet.getString(2));
                songSet.setArtistID(resultSet.getInt(3));
                songSet.setLanguage(resultSet.getString(4));
                songSet.setGenre(resultSet.getString(5));
                songSet.setSongURL(resultSet.getString(6));
                songSet.setSongLyricsURL(resultSet.getString(7));
                songSet.setSongImageURL(resultSet.getString(8));
                songSet.setAlbumID(resultSet.getInt(9));
                songSet.setReleaseDate(resultSet.getString(10));
                songSet.setSongRating(resultSet.getDouble(11));
                //adding this song object to list of song type
                System.out.print("*  ");
                userchoiceSong.add(songSet);
            }
            return userchoiceSong;
        } catch (SQLException e) {
            //displaying error if occured _
            e.printStackTrace();
        }
        return userchoiceSong;
    }

    /**
     * To return  recent added songs(5 days back) to the server!!!
     * will be ordered by release Date
     * no of rows to be queried controlled using rowcount
     */
    public static List<Song> showRecentAddedSongs(SongFetchRequest songFetchRequest) {
        int rowcount = songFetchRequest.getRowcount();
        int offset = songFetchRequest.getOffset();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        System.out.println(timestamp);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());

        // subtract 5 days
        cal.add(Calendar.DAY_OF_MONTH, -5);
        timestamp = new Timestamp(cal.getTime().getTime());

        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM artist " +
                "INNER JOIN songs ON artist.IDartist = songs.IDartist " +
                " WHERE songs.releaseDate > \" " + timestamp + "\" " +
                " ORDER BY songs.releaseDate DESC  " +
                "LIMIT " + offset + " , " + rowcount + " ;";


        String query1;
        List<Song> recentSongList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Song songSet;

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

                //adding this song object to list of song type
                recentSongList.add(songSet);
            }
            return recentSongList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return recentSongList;
    }

    /**
     * function for returning back recently played song by the user
     *
     * @param songFetchRequest
     * @return
     */

    public static Song showLastPlayedSong(SongFetchRequest songFetchRequest) {
        String email = songFetchRequest.getEmail();
        System.out.print(email + " :)");

        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "INNER JOIN artist ON  songs.IDartist=artist.IDartist " +
                "INNER JOIN user_history ON songs.IDsong=user_history.song_ID " +
                " WHERE user_history.user_email =\"" + email + "\" " +
                " ORDER BY user_history.time_played DESC " +
                " LIMIT 0,1;";


        Song songSet = null;

        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                songSet = new Song();
                do {
                    System.out.print(">> ");
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
                } while (resultSet.next());
            }

            return songSet;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return songSet;
    }

    /**
     * function to return back list of recently played songs by a particular user!!
     * query size limited by rowcount!!
     *
     * @param songFetchRequest
     * @return
     */
    public static List<Song> showRecentlyPlayedSong(SongFetchRequest songFetchRequest) {
        String email = songFetchRequest.getEmail();
        System.out.print(email + " :)");
        int rowcount = songFetchRequest.getRowcount();
        int offset = songFetchRequest.getOffset();

        String query = "SELECT  artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "INNER JOIN artist ON  songs.IDartist=artist.IDartist " +
                "INNER JOIN user_history ON songs.IDsong=user_history.song_ID " +
                " WHERE user_history.user_email =\"" + email + "\" " +
                " GROUP BY user_history.SONG_ID  " +
                " ORDER BY MAX(user_history.time_played) DESC " +
                "LIMIT " + offset + " , " + rowcount + " ;";
        Song songSet;
        List<Song> recentlyPlayedSongs = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                songSet = new Song();
                System.out.print(">> ");
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
                recentlyPlayedSongs.add(songSet);
            }
            return recentlyPlayedSongs;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return recentlyPlayedSongs;
    }

    /**
     * function to return back list of most played songs by a particular user!!ONLY 5
     * rn we have returned listof userHistory
     * i.e details are songID ,SONGnAME, NOoFtimesPlayed
     *
     * @param songFetchRequest
     * @return
     */
    public static List<UserHistory> showMostPlayedSongByUser(SongFetchRequest songFetchRequest) {
        String email = songFetchRequest.getEmail();
        System.out.print(" :)");
        String query = "SELECT songs.IDsong,songs.songName,COUNT(user_history.song_ID)" +
                " FROM songs" +
                " INNER JOIN user_history ON songs.IDsong=user_history.song_ID" +
                " WHERE user_history.user_email=\"" + email + "\" " +
                " GROUP BY songs.IDsong" +
                " ORDER BY COUNT(user_history.song_ID) DESC " +
                " LIMIT 0,5;";
        UserHistory mostPlayed;
        List<UserHistory> mostPlayedList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mostPlayed = new UserHistory();
                mostPlayed.setSongId(resultSet.getInt(1));
                mostPlayed.setSongName(resultSet.getString(2));
                mostPlayed.setNumberOfTimesPlayed(resultSet.getInt(3));
                mostPlayedList.add(mostPlayed);
            }
            return mostPlayedList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();


        }
        return mostPlayedList;
    }

    /**
     * function to return history of user
     * no of times a song played and blah blah . . .
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
     * for creating playlist for user
     * convention is as follows
     * if privacy public means 1 if private then 0
     * same in category
     * if group playlist then 1 ;if user's then 0
     */

    public static String creatingPlaylist(PlaylistRequest playlistRequest) {
        String privacy = playlistRequest.getPrivacy();
        String category = playlistRequest.getCategory();
        int pri, cat;
        if (privacy.equalsIgnoreCase("PUBLIC"))
            pri = 1;
        else
            pri = 0;
        if (category.equalsIgnoreCase("GROUP"))
            cat = 1;
        else
            cat = 0;
        Date date = new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class
        Timestamp timeCreated = new Timestamp(time);
        /**
         * thru this query we first check if aready playlist with same name for this user exists or not
         * if exists we wont create for him and ask him to choose some other name
         * and if no such exists then we will create with the name of playlist he specified
         * *_* *_* *_* *_*
         */
        String query1 = " SELECT * FROM " + DatabaseConstants.PLAYLIST_TABLE +
                " WHERE " + DatabaseConstants.PLAYLIST_COL_NAME + "=\"" + playlistRequest.getPlaylistName() + "\"" +
                " AND " + DatabaseConstants.PLAYLIST_COL_OWNER + "=\"" + playlistRequest.getEmail() + "\"";
        try {
            PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next())
                return String.valueOf(Status.ALREADY_EXIST);
            else {
                String query = "INSERT INTO " + DatabaseConstants.PLAYLIST_TABLE +
                        "(" + DatabaseConstants.PLAYLIST_COL_NAME +
                        "," + DatabaseConstants.PLAYLIST_COL_OWNER +
                        "," + DatabaseConstants.PLAYLIST_COL_CREATED +
                        "," + DatabaseConstants.PLAYLIST_COL_CATEGORY +
                        "," + DatabaseConstants.PLAYLIST_COL_PRIVACY +
                        ") values(?,?,?,?,?);";
                try {
                    PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
                    preparedStatement.setString(1, playlistRequest.getPlaylistName());
                    preparedStatement.setString(2, playlistRequest.getEmail());
                    preparedStatement.setTimestamp(3, timeCreated);
                    preparedStatement.setInt(4, cat);
                    preparedStatement.setInt(5, pri);

                    preparedStatement.executeUpdate();
                    System.out.println("success");
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
     * it is to get all the playlists that belong to him
     * rn this code gives only playlists which he has created
     * for the playlists he owns only membership note not ownership
     * for that case either will make another function
     * or will modify this
     * lets see what ill do
     */
    public static List<Playlist> getUserPlaylist(PlaylistRequest playlistRequest) {
        int pri, cat;
        String query = "Select * " +
                " FROM " + DatabaseConstants.PLAYLIST_TABLE +
                " LEFT JOIN membersOfGroupPlaylist ON playlist.id=membersOfGroupPlaylist.playlistID" +
                " WHERE " + DatabaseConstants.PLAYLIST_COL_OWNER + "=\"" + playlistRequest.getEmail() + "\"" +
                " OR membersOfGroupPlaylist.userEmail =\"" + playlistRequest.getEmail() + "\";";
        List<Playlist> myPlaylists = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            Playlist playlist;
            while (resultSet.next()) {
                playlist = new Playlist();
                playlist.setId(resultSet.getInt("id"));
                playlist.setPlaylistName(resultSet.getString(DatabaseConstants.PLAYLIST_COL_NAME));
                playlist.setOwner(resultSet.getString(DatabaseConstants.PLAYLIST_COL_OWNER));
                playlist.setDateCreated(resultSet.getTimestamp(DatabaseConstants.PLAYLIST_COL_CREATED));
                pri = resultSet.getInt(DatabaseConstants.PLAYLIST_COL_PRIVACY);
                cat = resultSet.getInt(DatabaseConstants.PLAYLIST_COL_CATEGORY);
                if (pri == 1)
                    playlist.setPrivacy("PUBLIC");
                else
                    playlist.setPrivacy("PRIVATE");
                if (cat == 1)
                    playlist.setCategory("GROUP");
                else
                    playlist.setCategory("USER'S");
                myPlaylists.add(playlist);
            }
            return myPlaylists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myPlaylists;

    }

    /**
     * this func is to add a song to playlist user wants to
     * checks
     * if song already exists in that particular playlist we wont add
     * and return string -->> ALREADY EXISTS
     */

    public static String addingSongToPlaylist(PlaylistRequest playlistRequest) {
        /**
         * thru this query we first check if aready playlist with same name for this user exists or not
         * if exists we wont create for him and ask him to choose some other name
         * and if no such exists then we will create with the name of playlist he specified
         * *_* *_* *_* *_*
         */
        String query1 = " SELECT * FROM " + DatabaseConstants.PLAYLIST_SONG_TABLE +
                " WHERE " + DatabaseConstants.PLAYLIST_SONG_COL_PLAYLIST_ID + "=\"" + playlistRequest.getPlaylistId() + "\"" +
                " AND " + DatabaseConstants.PLAYLIST_SONG_COL_SONG_ID + "=\"" + playlistRequest.getSongId() + "\"";
        try {
            PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next())
                return String.valueOf(Status.ALREADY_EXIST);
            else {
                String query = "INSERT INTO " + DatabaseConstants.PLAYLIST_SONG_TABLE +
                        "(" + DatabaseConstants.PLAYLIST_SONG_COL_PLAYLIST_ID +
                        "," + DatabaseConstants.PLAYLIST_SONG_COL_SONG_ID +
                        ") values(?,?);";
                try {
                    PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
                    preparedStatement.setInt(1, playlistRequest.getPlaylistId());
                    preparedStatement.setInt(2, playlistRequest.getSongId());

                    preparedStatement.executeUpdate();
                    System.out.println("song added");
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
     * if req is to get songs of a particular playlist
     * TODO CHECKS
     *
     * @param playlistRequest
     * @return
     */
    public static List<Song> getSongsOfPlaylist(PlaylistRequest playlistRequest) {

        String query = "SELECT  artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "INNER JOIN artist ON  songs.IDartist=artist.IDartist " +
                "INNER JOIN songsOfPlaylist ON  songs.IDsong=songsOfPlaylist.songID " +
                " WHERE songsOfPlaylist.playlistID =\"" + playlistRequest.getPlaylistId() + "\" " +
                " ORDER BY songs.IDsong DESC ";

        Song songSet;
        List<Song> playlistSongsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                songSet = new Song();
                System.out.print(">> ");
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

                playlistSongsList.add(songSet);
            }
            return playlistSongsList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return playlistSongsList;

    }

    /**
     * when u want to delete a playlist :ILL SERVE YOUR PURPOSE HIHI
     * first of all i check that u r requesting to delete f9 but do u have ownwership
     * if yes then ill serve ur purpose
     * otherwise sorry
     *
     * @param playlistRequest
     * @return
     */
    public static String deletePlaylist(PlaylistRequest playlistRequest) {
        String query1 = " SELECT * FROM " + DatabaseConstants.PLAYLIST_TABLE +
                " WHERE " + DatabaseConstants.PLAYLIST_COL_ID + "=\"" + playlistRequest.getPlaylistId() + "\"" +
                " AND " + DatabaseConstants.PLAYLIST_COL_OWNER + "=\"" + playlistRequest.getEmail() + "\"";
        try {
            PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                String query = "DELETE playlist , songsOfPlaylist,membersOfGroupPlaylist" +
                        " FROM playlist" +
                        " LEFT JOIN songsOfPlaylist ON playlist.id=songsOfPlaylist.playlistID" +
                        " LEFT JOIN membersOfGroupPlaylist ON playlist.id=membersOfGroupPlaylist.playlistID" +
                        " WHERE playlist.id=\"" + playlistRequest.getPlaylistId() + "\"";
                try {
                    PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    System.out.println("Playlist Deleted");
                    return String.valueOf(Status.SUCCESS);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return String.valueOf(Status.FAILED);
            }
//if u r not owner then we cant delete
            else {
                return String.valueOf(Status.NOT_OWNER);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);


    }


    /**
     * *for sending notification to the user whom our client wants to add as member of a
     * particular playlist
     */
    public static String sendingNotification(NotificationRequest notificationRequest) {
        //for checking if such receiver exists in our db or not
        String query="SELECT * FROM user_auth"+
                " WHERE email=\""+notificationRequest.getReceiver()+"\";";
        try{
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            ResultSet resultset=preparedStatement.executeQuery();
            if(resultset.next()){
                //if receiver exists we need to check that whether he has received any prior notification
                //to become member of this particular playlist
                query = " SELECT * FROM " + DatabaseConstants.NOTIFICATION_TABLE +
                        " WHERE " + DatabaseConstants.NOTIFICATION_COL_PLAYLIST_ID + "=\"" + notificationRequest.getPlaylistID() + "\"" +
                        " AND " + DatabaseConstants.NOTIFICATION_COL_RECEIVER + "=\"" + notificationRequest.getReceiver()+ "\"";
                try {
                    PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    if (resultSet.next())
                        return String.valueOf(Status.ALREADY_SENT);
                    else {
                        //if earlier not sent we will send it now
                        query = "INSERT INTO " + DatabaseConstants.NOTIFICATION_TABLE +
                                "(" + DatabaseConstants.NOTIFICATION_COL_SENDER +
                                "," + DatabaseConstants.NOTIFICATION_COL_RECEIVER +
                                "," + DatabaseConstants.NOTIFICATION_COL_PLAYLIST_ID +
                                ") values(?,?,?);";
                        try {
                            preparedStatement = Main.connection.prepareStatement(query);
                            preparedStatement.setString(1, notificationRequest.getSender());
                            preparedStatement.setString(2, notificationRequest.getReceiver());
                            preparedStatement.setInt(3, notificationRequest.getPlaylistID());
                            preparedStatement.executeUpdate();
                            System.out.println("sent");
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
            else
                return String.valueOf(Status.NO_SUCH_USER_EXIST);
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }

    /**
     * for returning back list of notifications that this particular has received
     * @param notificationRequest
     * @return
     */
    public static List<Notification> gettingNotification(NotificationRequest notificationRequest) {

        String query = "SELECT notification.sender,notification.playlistID,playlist.playlist_name " +
                "FROM notification " +
                " INNER JOIN playlist ON notification.playlistID=playlist.id" +
                " WHERE notification.receiver =\"" + notificationRequest.getReceiver() + "\" " +
                ";";


        Notification notification;
        List<Notification> notificationList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                notification = new Notification();
                notification.setSender(resultSet.getString(DatabaseConstants.NOTIFICATION_COL_SENDER));
                notification.setPlaylistName(resultSet.getString(3));
                notification.setId(resultSet.getInt(2));

                notificationList.add(notification);
            }
            return notificationList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }

        return notificationList;

    }

    /**
     * here we confirm the notification received !!
     * if already a member corr msg given
     *
     * @param notificationRequest
     * @return
     */
    public static String confirmNotification(NotificationRequest notificationRequest) {
        /**
         * thru this query we first check if aready this user is member of this playlist or not
         * *_* *_* *_* *_*
         */
        String query1 = " SELECT * FROM " + DatabaseConstants.PLAYLIST_MEMBER_TABLE +
                " WHERE " + DatabaseConstants.PLAYLIST_MEMBER_COL_PLAYLIST_ID + "=\"" + notificationRequest.getPlaylistID() + "\"" +
                " AND " + DatabaseConstants.PLAYLIST_MEMBER_COL_MEMBEREMAIL + "=\"" + notificationRequest.getReceiver() + "\"";
        try {
            PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next())
                return String.valueOf(Status.ALREADY_EXIST);
            else {
                String query = "INSERT INTO " + DatabaseConstants.PLAYLIST_MEMBER_TABLE +
                        "(" + DatabaseConstants.PLAYLIST_MEMBER_COL_PLAYLIST_ID +
                        "," + DatabaseConstants.PLAYLIST_MEMBER_COL_MEMBEREMAIL +
                        ") values(?,?);";
                try {
                    PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
                    preparedStatement.setInt(1, notificationRequest.getPlaylistID());
                    preparedStatement.setString(2, notificationRequest.getReceiver());

                    preparedStatement.executeUpdate();
                    System.out.println("Added AS MEMBER");
                    //TODO DELETE THAT PARTICULAR NOTIFICATION ALSO
                    query = "DELETE notification" +
                            " FROM notification" +
                            " WHERE notification.playlistID=\"" + notificationRequest.getPlaylistID() + "\"" +
                            " AND notification.receiver=\"" + notificationRequest.getReceiver() + "\";";
                    try {
                        preparedStatement = Main.connection.prepareStatement(query);
                        preparedStatement.executeUpdate();
                        System.out.println("This corr noti also deleted");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
     * function is to delete a notification which a user (receiver) dont wish to be present in the app anymore!
     */
    public static String deleteNotification(NotificationRequest notificationRequest) {
        String query = "DELETE notification" +
                " FROM notification" +
                " WHERE notification.playlistID=\"" + notificationRequest.getPlaylistID() + "\"" +
                " AND notification.receiver=\"" + notificationRequest.getReceiver() + "\";";
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            System.out.println("This  notification  deleted");
            return String.valueOf(Status.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return String.valueOf(Status.FAILED);

    }
}