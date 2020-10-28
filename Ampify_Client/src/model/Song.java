package model;



import java.io.Serializable;

public class Song implements Serializable {

    private int songID;
    private String songName;
    private String songImageURL;
    private  String songURL;
    private  String songLyricsURL;
    private double songRating;
    private int artistID;
    private int albumID;
    private String language;
    private String genre;
    private String releaseDate;
    private String artistName;

    public String getArtistName() {

        return artistName;
    }

    public void setArtistName(String artistName) {

        this.artistName = artistName;
    }

    public String getLanguage() {

        return language;
    }

    public void setLanguage(String language) {

        this.language = language;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    public String getReleaseDate() {

        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {

        this.releaseDate = releaseDate;
    }

    public void setSongID(int songID) {

        this.songID = songID;
    }

    public void setSongName(String songName) {

        this.songName = songName;
    }

    public void setSongImageURL(String songImageURL) {

        this.songImageURL = songImageURL;
    }

    public void setSongURL(String songURL) {

        this.songURL = songURL;
    }

    public void setSongLyricsURL(String songLyricsURL) {

        this.songLyricsURL = songLyricsURL;
    }

    public void setSongRating(double songRating) {

        this.songRating = songRating;
    }

    public void setArtistID(int artistID) {

        this.artistID = artistID;
    }

    public void setAlbumID(int albumID) {

        this.albumID = albumID;
    }

    public int getSongID() {

        return songID;
    }

    public String getSongName() {

        return songName;
    }

    public String getSongImageURL() {

        return songImageURL;
    }

    public String getSongURL() {

        return songURL;
    }

    public String getSongLyricsURL() {

        return songLyricsURL;
    }

    public double getSongRating() {

        return songRating;
    }

    public int getArtistID() {

        return artistID;
    }

    public int getAlbumID() {

        return albumID;
    }

    @Override
    public String toString() {

        return this.songName;
    }


}

