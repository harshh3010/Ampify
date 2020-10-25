package Services;

import mainClass.Main;
import model.Album;
import model.Artist;
import model.Song;
import model.UserAuth;
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
    public static int offsetTopsong=0;
    public static int offsetSongOfParticularArtist=0;
    public static int offsetSongOfParticularAlbum=0;
    public static  int rowcount=10;
    public static int offsetUserChoiceSongs=0;
    public static int offsetRecentSongs=0;

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
    public static List<Song> getTopSongs() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.TOP),offsetTopsong,rowcount);
        oos.writeObject(songFetchRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }


    /**
    Function to fetch songs of a particular artist
     *also pass the offset coz server needs to know from which row number it has to start its job
     */
    public static List<Song> getSongsOfArtist(int artistId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST), artistId,offsetSongOfParticularArtist,rowcount);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }


    /**
    Function to fetch songs of a particular album
     *also pass the offset coz server needs to know from which row number it has to start its job
     */
    public static List<Song> getSongsOfAlbum(int albumId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ALBUM), albumId,offsetSongOfParticularAlbum,rowcount);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();

        return (List<Song>) ois.readObject();
    }


    /**
    Function to fetch songs of user choice
     *we pass row count , offset specific to this kind of fetching request
     * defined above
     *
    */
    public static List<Song> getUserChoiceSongs() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_USER_CHOICE), userApi.getEmail(),offsetUserChoiceSongs,rowcount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }


    /**
Function to fetch recent songs(released 5 days back!!)
 */
    public static List<Song> getRecentSongs() throws IOException, ClassNotFoundException {
        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.RECENT_SONGS),offsetRecentSongs,rowcount);
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }
    /**
     * Function to add to history table a particular song played by user and sets is_playing attribute of a song to true
     * */
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


}
