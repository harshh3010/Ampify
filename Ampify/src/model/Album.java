package model;

import java.io.Serializable;

public class Album implements Serializable {
    private int albumID;
    private String albumName;
    private String releaseDate;
    private double rating;

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {

        this.albumName = albumName;
    }

    public String getReleaseDate() {

        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {

        this.releaseDate = releaseDate;
    }

    public double getRating() {

        return rating;
    }

    public void setRating(double rating) {

        this.rating = rating;
    }
    @Override
    public String toString() {

        return this.albumName;
    }
}
