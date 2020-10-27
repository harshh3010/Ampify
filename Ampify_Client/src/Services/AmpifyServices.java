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
    public static int offsetSongOfParticularArtist = 0;
    public static int offsetSongOfParticularAlbum = 0;
    public static int rowcount = 10;
    public static int offsetUserRecentlyPlayedSong = 0;

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


    /**
     * Function to fetch songs of a particular artist
     * also pass the offset coz server needs to know from which row number it has to start its job
     */
    public static List<Song> getSongsOfArtist(int artistId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST), artistId, offsetSongOfParticularArtist, rowcount);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }


    /**
     * Function to fetch songs of a particular album
     * also pass the offset coz server needs to know from which row number it has to start its job
     */
    public static List<Song> getSongsOfAlbum(int albumId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ALBUM), albumId, offsetSongOfParticularAlbum, rowcount);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }


    /**
     * Function to fetch songs of user choice
     * we pass row count , offset specific to this kind of fetching request
     * defined above
     */
    public static List<Song> getUserChoiceSongs(int offset, int rowCount) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_USER_CHOICE), userApi.getEmail(), offset, rowCount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /**
     * Function to fetch last played song of user
     * we pass email of the current user logged in
     * taken from UserApi saved instance
     * *
     */
    public static Song getUserLastPlayedSong() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.LAST_PLAYED_SONG), userApi.getEmail());
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (Song) ois.readObject();
    }

    /**
     * Function to fetch RECENTY played song of user
     * we pass email of the current user logged in
     * taken from UserApi saved instance
     * *
     */
    public static List<Song> getUserRecentlyPlayedSong() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.RECENT_PLAYED_SONGS_BY_USER), userApi.getEmail(), offsetUserRecentlyPlayedSong, rowcount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }
    /**
     * Function to fetch RECENTY played song of user
     * we pass email of the current user logged in
     * taken from UserApi saved instance
     * *we will return only 5 songs for this !!
     */
    public static List<UserHistory> getUserMostPlayedSong() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.MOST_PLAYED_SONGS_BY_USER), userApi.getEmail());
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<UserHistory>) ois.readObject();
    }


    /**
     * Function to fetch recent songs(released 5 days back!!)
     */
    public static List<Song> getRecentAddedSongs(int offset, int rowCount) throws IOException, ClassNotFoundException {
        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.RECENT_ADDED_SONGS), offset, rowCount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }

    /**
     * Function to add to history table a particular song played by user and sets is_playing attribute of a song to true
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

    /**
     * Function to fetch user history
     * we pass email of user logged in
     * also offset to manage the number of rows to be queried passed( defined above)
     */
    public static List<UserHistory> getUserHistory(int offset, int rowCount) throws IOException, ClassNotFoundException {

        FetchUserHistoryRequest fetchUserHistory = new FetchUserHistoryRequest(userApi.getEmail(), offset, rowCount);
        oos.writeObject(fetchUserHistory);
        oos.flush();
        ois = Main.userInputStream;

        return (List<UserHistory>) ois.readObject();
    }

    /**
     * this is to create playlist for the user
     * convention is as follows
     * if privacy public means 1 if private then 0
     * same in category
     * if group playlist then 1 ;if user's then 0
     */
    public static String createPlaylist(String playlistName, String category, String privacy) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.CREATE_PLAYLIST), playlistName, userApi.getEmail(), privacy, category);

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }
//for fetching personal plylists
    public static List<Playlist> getMyPlaylists() throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.FETCH_USER_PLAYLISTS), userApi.getEmail());

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (List<Playlist>) ois.readObject();

    }

    //for fetching group playlists!!
    public static List<Playlist> getMyGroupPlaylists() throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.FETCH_GROUP_PLAYLISTS), userApi.getEmail());

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (List<Playlist>) ois.readObject();

    }

    /**
     * this is to add song to the playlist user wants to
     * we require the playlist ID he wants to add song to
     * and song ID
     * also checks in the backend required
     * that if he has membership,or ownership for that particular playlist then only allow him to add the song
     * or m2 is :: when
     * *when he requests to add song to playlist
     * *we will display only the playlists he belong to
     * * so no chance of his adding to the playlist of which he is either owner nor mwmber
     * TODO DISCUSS METHOD WHICH NEEDS TO BE FOLLOWED
     */
    public static String addSongToPlaylist(int playlistID, int songID) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.ADD_SONG_TO_A_PLAYLIST), playlistID, songID);

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /**
     * this func is to get sngs of a particcular playlist(playist ID imp)
     * no matter it's user's playlist or not
     * u can view its songs if public
     * (TODO PUBLIC PLAYLISTS TO BE VIEWED ONLY IF NOT MEMBER OR ELSE CAN VIEW if its his own playlist)
     */

    public static List<Song> getSongsOfPlaylist(int playlistID) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.FETCH_SONGS_OF_A_PLAYLIST), playlistID);

        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (List<Song>) ois.readObject();

    }

    /**
     * for deleting a paricular playlist
     * @param playlistID
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static String deletePlaylist(int playlistID) throws IOException, ClassNotFoundException {
        PlaylistRequest playlistRequest = new PlaylistRequest(String.valueOf(PlaylistType.DELETE_PLAYLIST),userApi.getEmail(), playlistID);
        oos.writeObject(playlistRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /**
     **for sending notification to the user whom our client wants to add as member of a
     * particular playlist
     * sender==userApi.getEmail()
     * receiver, playistID to be passed to this function
     */
    public static String sendNotification(String receiver,int playlistID)throws IOException, ClassNotFoundException {
        System.out.print("jj");
        NotificationRequest notificationRequest=new NotificationRequest(String.valueOf(NotificationType.SEND),userApi.getEmail(),receiver,playlistID);
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }

    /**
     * for receiving all the notifications present in server sent to user currently logged in
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Notification> getMyNotifications()throws IOException, ClassNotFoundException {

        NotificationRequest notificationRequest=new NotificationRequest(String.valueOf(NotificationType.GET_NOTIFICATIONS),userApi.getEmail());
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (List<Notification>) ois.readObject();
    }
    /**
     * for confirming a particular notification
     * task is to add this user as member in that particular playlist
     */
    public static String confirmNotification(int playlistID)throws IOException, ClassNotFoundException {

        NotificationRequest notificationRequest=new NotificationRequest(String.valueOf(NotificationType.CONFIRM_NOTIFICATION),userApi.getEmail(),playlistID);
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }
    /**
     * for deleting a particular notification
     * playlistID and receiver are enough to idenify (both gr by primary)
     */
    public static String deleteNotification(int playlistID)throws IOException, ClassNotFoundException {

        NotificationRequest notificationRequest=new NotificationRequest(String.valueOf(NotificationType.DELETE_NOTIFICATION),userApi.getEmail(),playlistID);
        oos.writeObject(notificationRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (String) ois.readObject();
    }










}
