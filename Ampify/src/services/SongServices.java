package services;

import model.Song;
import serverClasses.Main;
import serverClasses.requests.SongFetchRequest;
import utilities.DatabaseConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SongServices {
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
                "FROM songs " +
                "INNER JOIN artist ON songs.IDartist=artist.IDartist  " +
                " ORDER BY songs.rating DESC  " +
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
                "FROM songs " +
                "INNER JOIN artist ON songs.IDartist=artist.IDartist  " +
                "WHERE artist.IDartist =\"" + artistID + "\" " +
                "ORDER BY songs.IDsong " +
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
     * To return  songs of a particular album to UI!!!
     * */
    public static List<Song> showSongsOfParticularAlbum(SongFetchRequest songFetchRequest) {

        int albumID = songFetchRequest.getID();
        int offset = songFetchRequest.getOffset();
        int rowcount = songFetchRequest.getRowcount();
        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "INNER JOIN artist ON songs.IDartist=artist.IDartist  " +
                "WHERE album.IDalbum =\"" + albumID + "\" " +
                "ORDER BY songs.IDsong" +
                " LIMIT " + offset + " , " + rowcount + ";";


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

        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "INNER JOIN artist ON songs.IDartist=artist.IDartist" +
                " WHERE songs.languages IN  (" + languagest +
                ") OR songs.genre IN (" + genrest +
                ") OR songs.IDartist  IN (" + artistst + ") " +
                " GROUP BY songs.IDsong  " +
                " ORDER BY songs.IDsong DESC " +
                "LIMIT " + offset + " , " + rowcount + ";";


        String query1;
        List<Song> userChoiceSongList = new ArrayList<>();
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
                userchoiceSong.add(songSet);
            }
            return userchoiceSong;
        } catch (SQLException e) {
            //displaying error if occured *_*
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


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());

        // subtract 5 days
        cal.add(Calendar.DAY_OF_MONTH, -5);
        timestamp = new Timestamp(cal.getTime().getTime());

        String query = "SELECT artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "INNER JOIN artist ON songs.IDartist=artist.IDartist  " +
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
                " GROUP BY songs.IDsong  " +
                " ORDER BY MAX(user_history.time_played) DESC " +
                "LIMIT " + offset + " , " + rowcount + " ;";
        Song songSet;
        List<Song> recentlyPlayedSongs = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();


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
    public static List<Song> showMostPlayedSongByUser(SongFetchRequest songFetchRequest) {
        String email = songFetchRequest.getEmail();

        String query = "SELECT  artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong,COUNT(user_history.song_ID)" +
                " FROM songs" +
                " INNER JOIN artist ON  songs.IDartist=artist.IDartist " +
                " INNER JOIN user_history ON songs.IDsong=user_history.song_ID" +
                " WHERE user_history.user_email=\"" + email + "\" " +
                " GROUP BY songs.IDsong" +
                " ORDER BY COUNT(user_history.song_ID) DESC " +
                " LIMIT 0,5;";
        Song mostPlayed;
        List<Song> mostPlayedList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mostPlayed = new Song();
                mostPlayed.setSongID(resultSet.getInt(DatabaseConstants.SONG_COL_ID));
                mostPlayed.setSongName(resultSet.getString(DatabaseConstants.SONG_COL_NAME));
                mostPlayed.setArtistID(resultSet.getInt(DatabaseConstants.SONG_COL_ARTISTID));
                mostPlayed.setLanguage(resultSet.getString(DatabaseConstants.SONG_COL_LANGUAGE));
                mostPlayed.setGenre(resultSet.getString(DatabaseConstants.SONG_COL_GENRES));
                mostPlayed.setSongURL(resultSet.getString(DatabaseConstants.SONG_COL_MUSIC_URL));
                mostPlayed.setSongLyricsURL(resultSet.getString(DatabaseConstants.SONG_COL_LYRICS_URL));
                mostPlayed.setSongImageURL(resultSet.getString(DatabaseConstants.SONG_COL_IMAGE_URL));
                mostPlayed.setAlbumID(resultSet.getInt(DatabaseConstants.SONG_COL_ALBUMID));
                mostPlayed.setReleaseDate(resultSet.getString(DatabaseConstants.SONG_COL_RELEASE_DATE));
                mostPlayed.setSongRating(resultSet.getDouble(DatabaseConstants.SONG_COL_RATING));
                mostPlayed.setArtistName(resultSet.getString(DatabaseConstants.ARTIST_COL_NAME));

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
     * function to return back list of trending songs!!ONLY 5
     * rn we have returned listof userHistory
     * i.e details are songID ,SONGnAME, NOoFtimesPlayed
     *
     * @param songFetchRequest
     * @return
     */
    public static List<Song> showTrendingSongs(SongFetchRequest songFetchRequest) {

        Timestamp timestamp = new Timestamp(new Date().getTime());


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());

        // subtract 1 day
        cal.add(Calendar.DAY_OF_MONTH, -1);
        timestamp = new Timestamp(cal.getTime().getTime());



        String query = "SELECT  artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong,COUNT(user_history.song_ID)" +
                " FROM songs" +
                " INNER JOIN artist ON  songs.IDartist=artist.IDartist " +
                " INNER JOIN user_history ON songs.IDsong=user_history.song_ID" +
                " WHERE user_history.time_played >=\"" + timestamp + "\" " +
                " GROUP BY songs.IDsong" +
                " ORDER BY COUNT(user_history.song_ID) DESC " +
                " LIMIT 0,5;";
        Song trendingSongs;
        List<Song> trendingSongsList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                trendingSongs = new Song();
                trendingSongs.setSongID(resultSet.getInt(DatabaseConstants.SONG_COL_ID));
                trendingSongs.setSongName(resultSet.getString(DatabaseConstants.SONG_COL_NAME));
                trendingSongs.setArtistID(resultSet.getInt(DatabaseConstants.SONG_COL_ARTISTID));
                trendingSongs.setLanguage(resultSet.getString(DatabaseConstants.SONG_COL_LANGUAGE));
                trendingSongs.setGenre(resultSet.getString(DatabaseConstants.SONG_COL_GENRES));
                trendingSongs.setSongURL(resultSet.getString(DatabaseConstants.SONG_COL_MUSIC_URL));
                trendingSongs.setSongLyricsURL(resultSet.getString(DatabaseConstants.SONG_COL_LYRICS_URL));
                trendingSongs.setSongImageURL(resultSet.getString(DatabaseConstants.SONG_COL_IMAGE_URL));
                trendingSongs.setAlbumID(resultSet.getInt(DatabaseConstants.SONG_COL_ALBUMID));
                trendingSongs.setReleaseDate(resultSet.getString(DatabaseConstants.SONG_COL_RELEASE_DATE));
                trendingSongs.setSongRating(resultSet.getDouble(DatabaseConstants.SONG_COL_RATING));
                trendingSongs.setArtistName(resultSet.getString(DatabaseConstants.ARTIST_COL_NAME));

                trendingSongsList.add(trendingSongs);
            }
            return trendingSongsList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();


        }
        return trendingSongsList;
    }

    /**
     * function to return back list of previously played songs!!ONLY 5
     * i.e details are songID ,SONGnAME, NOoFtimesPlayed
     *
     * @param songFetchRequest
     * @return
     */
    public static List<Song> showPreviouslyPlayedSongs(SongFetchRequest songFetchRequest) {
        String email = songFetchRequest.getEmail();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Timestamp timestamp1 = new Timestamp(new Date().getTime());


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timestamp1.getTime());

        // subtract 2 hour
        cal.add(Calendar.HOUR_OF_DAY, -2);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        //add 2 hour
        cal1.add(Calendar.HOUR_OF_DAY, +2);
        cal1.add(Calendar.DAY_OF_MONTH, -1);
        timestamp = new Timestamp(cal.getTime().getTime());
        timestamp1 = new Timestamp(cal1.getTime().getTime());

        String query = "SELECT  artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong,COUNT(user_history.song_ID)" +
                " FROM songs" +
                " INNER JOIN artist ON  songs.IDartist=artist.IDartist " +
                " INNER JOIN user_history ON songs.IDsong=user_history.song_ID" +
                " WHERE user_history.user_email=\"" + email + "\" " +
                " AND user_history.time_played >=\"" + timestamp + "\" " +
                " AND user_history.time_played <=\"" + timestamp1 + "\" " +
                " GROUP BY songs.IDsong" +
                " ORDER BY COUNT(user_history.song_ID) DESC " +
                " LIMIT 0,5;";
        Song previouslyPlayedSongs;
        List<Song> previouslyPlayedSongsList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                previouslyPlayedSongs = new Song();
                previouslyPlayedSongs.setSongID(resultSet.getInt(DatabaseConstants.SONG_COL_ID));
                previouslyPlayedSongs.setSongName(resultSet.getString(DatabaseConstants.SONG_COL_NAME));
                previouslyPlayedSongs.setArtistID(resultSet.getInt(DatabaseConstants.SONG_COL_ARTISTID));
                previouslyPlayedSongs.setLanguage(resultSet.getString(DatabaseConstants.SONG_COL_LANGUAGE));
                previouslyPlayedSongs.setGenre(resultSet.getString(DatabaseConstants.SONG_COL_GENRES));
                previouslyPlayedSongs.setSongURL(resultSet.getString(DatabaseConstants.SONG_COL_MUSIC_URL));
                previouslyPlayedSongs.setSongLyricsURL(resultSet.getString(DatabaseConstants.SONG_COL_LYRICS_URL));
                previouslyPlayedSongs.setSongImageURL(resultSet.getString(DatabaseConstants.SONG_COL_IMAGE_URL));
                previouslyPlayedSongs.setAlbumID(resultSet.getInt(DatabaseConstants.SONG_COL_ALBUMID));
                previouslyPlayedSongs.setReleaseDate(resultSet.getString(DatabaseConstants.SONG_COL_RELEASE_DATE));
                previouslyPlayedSongs.setSongRating(resultSet.getDouble(DatabaseConstants.SONG_COL_RATING));
                previouslyPlayedSongs.setArtistName(resultSet.getString(DatabaseConstants.ARTIST_COL_NAME));

                previouslyPlayedSongsList.add(previouslyPlayedSongs);
            }
            return previouslyPlayedSongsList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }
        return previouslyPlayedSongsList;
    }

    /**
     * function to return back list of favourite songs of a particular user!!
     * query size limited by rowcount!!
     */
    public static List<Song> showFavouriteSong(SongFetchRequest songFetchRequest) {
        String email = songFetchRequest.getEmail();
        int rowcount = songFetchRequest.getRowcount();
        int offset = songFetchRequest.getOffset();

        String query = "SELECT  artist.artistName,songs.songName," +
                "songs.languages,songs.genre,songs.musicURL, songs.lyricsURL," +
                "songs.imageURL,songs.releaseDate,songs.rating," +
                "songs.IDartist,songs.IDalbum,songs.IDsong " +
                "FROM songs " +
                "INNER JOIN artist ON  songs.IDartist=artist.IDartist " +
                "INNER JOIN favouriteSong ON songs.IDsong=favouriteSong.songID " +
                " WHERE favouriteSong.userEmail =\"" + email + "\" " +
                " ORDER BY songs.IDsong DESC " +
                "LIMIT " + offset + " , " + rowcount + " ;";
        Song songSet;
        List<Song> favouriteSongList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

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
                favouriteSongList.add(songSet);
            }
            return favouriteSongList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }
        return favouriteSongList;
    }



}
