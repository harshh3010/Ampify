package Services;

import mainClass.Main;
import model.*;
import serverClasses.requests.*;
import utilities.ArtistsAlbumFetchType;
import utilities.SongFetchType;
import utilities.UserApi;

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
    public static int offsetTopsong = 0;
    public static int offsetSongOfParticularArtist = 0;
    public static int offsetSongOfParticularAlbum = 0;
    public static int rowcount = 10;
    public static int offsetUserChoiceSongs = 0;
    public static int offsetRecentAddedSongs = 0;
    public static int offsetUserRecentlyPlayedSong = 0;
    public static int offsetUserHistory = 0;

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
    public static List<Song> getTopSongs(int offset,int rowCount) throws IOException, ClassNotFoundException {

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
    public static List<Song> getUserChoiceSongs(int offset,int rowCount) throws IOException, ClassNotFoundException {

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
     * Function to fetch recent songs(released 5 days back!!)
     */
    public static List<Song> getRecentAddedSongs(int offset,int rowCount) throws IOException, ClassNotFoundException {
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
    public static List<UserHistory> getUserHistory(int offset,int rowCount) throws IOException, ClassNotFoundException {

        FetchUserHistoryRequest fetchUserHistory = new FetchUserHistoryRequest(userApi.getEmail(), offset, rowCount);
        oos.writeObject(fetchUserHistory);
        oos.flush();
        ois = Main.userInputStream;

        return (List<UserHistory>) ois.readObject();
    }


}
