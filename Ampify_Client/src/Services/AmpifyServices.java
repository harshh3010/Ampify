package Services;

import mainClass.Main;
import model.Album;
import model.Artist;
import model.Song;
import serverClasses.requests.AlbumFetchRequest;
import serverClasses.requests.ArtistFetchRequest;
import serverClasses.requests.PlaySongRequest;
import serverClasses.requests.SongFetchRequest;
import utilities.ArtistsAlbumFetchType;
import utilities.SongFetchType;
import java.sql.Timestamp;
import java.util.Date;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class AmpifyServices {

    private static ObjectOutputStream oos = Main.userOutputStream;
    private static ObjectInputStream ois = Main.userInputStream;

    /*
    Function to get a list of top artists
     */
    public static List<Artist> getTopArtists() throws IOException, ClassNotFoundException {

        ArtistFetchRequest artistsFetchRequest = new ArtistFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
        oos.writeObject(artistsFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Artist>) ois.readObject();
    }


    /*
    Function to get a list of top albums
     */
    public static List<Album> getTopAlbums() throws IOException, ClassNotFoundException {

        AlbumFetchRequest albumFetchRequest = new AlbumFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
        oos.writeObject(albumFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Album>) ois.readObject();
    }


    /*
    Function to get top songs
     */
    public static List<Song> getTopSongs() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.TOP));
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }


    /*
    Function to fetch songs of a particular artist
     */
    public static List<Song> getSongsOfArtist(int artistId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST), artistId);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }


    /*
    Function to fetch songs of a particular album
     */
    public static List<Song> getSongsOfAlbum(int albumId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ALBUM), albumId);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }
/*
* Function to add to history table a particular song played by user and sets is_playing attribute of a song to true
* */
    public static PlaySongRequest playSong(String email,int songID) throws IOException, ClassNotFoundException {

        Date date= new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class
        Timestamp timePlayed = new Timestamp(time);

        PlaySongRequest playSongRequest = new PlaySongRequest(email,songID,timePlayed);
        oos.writeObject(playSongRequest);
        oos.flush();
        ois = Main.userInputStream;
        return (PlaySongRequest)ois.readObject();


    }


}
