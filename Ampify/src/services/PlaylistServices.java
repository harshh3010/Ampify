package services;

import model.Playlist;
import model.Song;
import serverClasses.Main;
import serverClasses.requests.PlaylistRequest;
import utilities.DatabaseConstants;
import utilities.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaylistServices {

    /**
     * for creating playlist for user
     * convention is as follows
     * if privacy -->public means 1 if private then 0
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
                //setting these values stored in playlist req object to our db playlist table!!
                try {
                    PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
                    preparedStatement.setString(1, playlistRequest.getPlaylistName());
                    preparedStatement.setString(2, playlistRequest.getEmail());
                    preparedStatement.setTimestamp(3, timeCreated);
                    preparedStatement.setInt(4, cat);
                    preparedStatement.setInt(5, pri);

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
     * it is to get all the playlists that belong to him
     *whether he is a owner
     * or is a member
     * this func will return all of those
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
               /* * some assumtions!!
               if privacy public means 1 if private then 0
                        * same in category
                * if group playlist then 1 ;if user's then 0
                * */
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
                    return String.valueOf(Status.SUCCESS);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return String.valueOf(Status.FAILED);
            }
            //if u r not owner then u cant delete
            else {
                return String.valueOf(Status.NOT_OWNER);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);


    }

}
