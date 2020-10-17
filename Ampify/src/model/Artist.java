package model;
import java.io.Serializable;

public class Artist implements Serializable{

    private String artistName;
    private String artistImageURL;
    private int artistID;
    private double artistRating;

    public Artist()
    {

    }
    public String getArtistName(){

        return  artistName;
    }
    public String getArtistImageURL(){

        return  artistImageURL;
    }
    public int getArtistID(){

        return  artistID;
    }
    public double getArtistRating(){

        return  artistRating;
    }
    public void setArtistName(String artistName){

        this.artistName=artistName;
    }
    public void setArtistImageURL(String artistImageURL){

        this.artistImageURL=artistImageURL;
    }
    public void setArtistID(int artistID){

        this.artistID=artistID;
    }
    public void setArtistRating(double artistRating){
        this.artistRating=artistRating;
    }
    @Override
    public String toString() {
        return this.artistName;
    }
}
