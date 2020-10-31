/*
Class to handle all the data transfer between server and client
*/

package Services;

import mainClass.Main;
import model.*;
import serverClasses.requests.*;
import utilities.*;

import java.sql.Timestamp;
import java.util.Date;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class AmpifyServices {

    private static ObjectOutputStream oos = Main.userOutputStream;
    private static ObjectInputStream ois = Main.userInputStream;
    private static UserApi userApi = UserApi.getInstance();

    /*
    Function to get a list of top artists
     */
    public static List<Artist> getTopArtists() throws IOException, ClassNotFoundException {

        ArtistFetchRequest artistsFetchRequest = new ArtistFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
        oos.writeObject(artistsFetchRequest);
        oos.flush();

        return (List<Artist>) ois.readObject();
    }

    /*
    Function to get a list of top albums
     */
    public static List<Album> getTopAlbums() throws IOException, ClassNotFoundException {

        AlbumFetchRequest albumFetchRequest = new AlbumFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
        oos.writeObject(albumFetchRequest);
        oos.flush();

        return (List<Album>) ois.readObject();
    }

    /*
    Function to get top songs
     */
    public static List<Song> getTopSongs(int offset, int rowCount) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.TOP), offset, rowCount);
        oos.writeObject(songFetchRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }

    /*
    Function to fetch songs of a particular artist
     */
    public static List<Song> getSongsOfArtist(int artistId,int offset,int rowCount) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST), artistId, offset, rowCount);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }

    /*
    Function to get songs of a particular album
     */
    public static List<Song> getSongsOfAlbum(int albumId,int offset,int rowCount) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ALBUM), albumId, offset, rowCount);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }

    /*
    Function to get songs of user's choice
     */
    public static List<Song> getUserChoiceSongs(int offset, int rowCount) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_USER_CHOICE), userApi.getEmail(), offset, rowCount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /*
    Function to get last played song by the user
    */
    public static Song getUserLastPlayedSong() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.LAST_PLAYED_SONG), userApi.getEmail());
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (Song) ois.readObject();
    }

    /*
    Function to fetch recently played songs by the user
     */
    public static List<Song> getUserRecentlyPlayedSong(int offset,int rowCount) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.RECENT_PLAYED_SONGS_BY_USER), userApi.getEmail(), offset, rowCount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /*
    Function to get user's most played songs
     */
    public static List<Song> getUserMostPlayedSong() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.MOST_PLAYED_SONGS_BY_USER), userApi.getEmail());
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /*
    Function to fetch trending songs
     */
    public static List<Song> getTrendingSongs() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.TRENDING_SONGS), userApi.getEmail());
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /*
    Function to fetch the songs played by user at same time in past
     */
    public static List<Song> getPreviouslyPlayedSongs() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.PREVIOUSLY_PLAYED_BY_USER), userApi.getEmail());
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /*
    Function to fetch recently added songs to the server
     */
    public static List<Song> getRecentAddedSongs(int offset, int rowCount) throws IOException, ClassNotFoundException {
        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.RECENT_ADDED_SONGS), offset, rowCount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /*
    Function to update song history
     */
    public static String addSongToHistory(int songID) throws IOException, ClassNotFoundException {

        Date date = new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class
        Timestamp timePlayed = new Timestamp(time);

        AddToHistoryRequest addToHistoryRequest = new AddToHistoryRequest(songID, userApi.getEmail(), timePlayed);
        oos.writeObject(addToHistoryRequest);
        oos.flush();

        return (String) ois.readObject();
    }

    /*
    Function to fetch history of songs played by the user
     */
    public static List<UserHistory> getUserHistory(int offset, int rowCount) throws IOException, ClassNotFoundException {

        FetchUserHistoryRequest fetchUserHistory = new FetchUserHistoryRequest(userApi.getEmail(), offset, rowCount);
        oos.writeObject(fetchUserHistory);
        oos.flush();
        ois = Main.userInputStream;

        return (List<UserHistory>) ois.readObject();
    }

    /*
    Function to create a playlist
    */
    public static String createPlaylist(String playlistName, String category, String privacy) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.CREATE_PLAYLIST), playlistName, userApi.getEmail(), privacy, category);

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /*
    Function to fetch user's playlists
     */
    public static List<Playlist> getMyPlaylists() throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.FETCH_USER_PLAYLISTS), userApi.getEmail());

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (List<Playlist>) ois.readObject();

    }

    /*
    Function to add a song to the playlist
     */
    public static String addSongToPlaylist(int playlistID, int songID) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.ADD_SONG_TO_A_PLAYLIST), playlistID, songID);

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /*
    Function to get songs of a particular playlist
     */
    public static List<Song> getSongsOfPlaylist(int playlistID) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.FETCH_SONGS_OF_A_PLAYLIST), playlistID);

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (List<Song>) ois.readObject();

    }

    /*
    Function to delete a playlist
    */
    public static String deletePlaylist(int playlistID) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.DELETE_PLAYLIST), userApi.getEmail(), playlistID);
        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /*
    Function to send an invitation to another user for joining a group playlist
    */
    public static String sendNotification(String receiver, int playlistID) throws IOException, ClassNotFoundException {
        System.out.print("jj");
        NotificationRequest notificationRequest = new NotificationRequest(String.valueOf(NotificationType.SEND), userApi.getEmail(), receiver, playlistID);
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /*
    Function to load invitations received by the user
    */
    public static List<Notification> getMyNotifications() throws IOException, ClassNotFoundException {

        NotificationRequest notificationRequest = new NotificationRequest(String.valueOf(NotificationType.GET_NOTIFICATIONS), userApi.getEmail());
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (List<Notification>) ois.readObject();
    }

    /*
    Function to accept an invitation
    */
    public static String confirmNotification(int playlistID) throws IOException, ClassNotFoundException {

        NotificationRequest notificationRequest = new NotificationRequest(String.valueOf(NotificationType.CONFIRM_NOTIFICATION), userApi.getEmail(), playlistID);
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /*
    Function to decline the invitation
     */
    public static String deleteNotification(int playlistID) throws IOException, ClassNotFoundException {

        NotificationRequest notificationRequest = new NotificationRequest(String.valueOf(NotificationType.DELETE_NOTIFICATION), userApi.getEmail(), playlistID);
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /*
    Function to fetch songs according to a query
     */
    public static List<Song> getSearchResult(String text,int offset, int rowCount) throws IOException, ClassNotFoundException {

        SearchRequest searchRequest = new SearchRequest(text, offset, rowCount);
        oos.writeObject(searchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /*
    Function to add a song to favourites
    */
    public static String addToFavorites(int songID) throws IOException, ClassNotFoundException {

        AddToFavouriteRequest addToFavouriteRequest = new AddToFavouriteRequest(songID, userApi.getEmail());
        oos.writeObject(addToFavouriteRequest);
        oos.flush();

        return (String) ois.readObject();
    }

    /*
    Function to fetch user's favourite songs
     */
    public static List<Song> getUserFavouriteSong(int offset,int rowCount) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.FAVOURITE_SONGS), userApi.getEmail(), offset, rowCount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

}
