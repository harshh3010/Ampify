package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class ArtistFetchRequest implements Serializable {

    /*private String artistName;
    private String artistImageURL;
    private int artistID;

    public ArtistFetchRequest()
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
    public void setArtistName(String artistName){
        this.artistName=artistName;
    }
    public void setArtistImageURL(String artistImageURL){
        this.artistImageURL=artistImageURL;
    }
    public void setArtistID(int artistID){
        this.artistID=artistID;
    }*/
    @Override
    public String toString() {
        return String.valueOf(ServerRequest.ARTIST_SHOW);
    }
}
