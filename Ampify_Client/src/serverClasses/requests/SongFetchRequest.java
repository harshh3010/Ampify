package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class SongFetchRequest implements Serializable {

    private String type;
    private int artistID;

    public SongFetchRequest(String type) {
        this.type = type;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }
//when u want to fetch songs of particular id
    //TODO Particulr ALBUM ID SONGS CAN ALSO BE ACCESSED THRU THIS CONSTRUCTOR *_*
    public SongFetchRequest(String type, int artistID) {
        this.type = type;
        this.artistID=artistID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {

        return String.valueOf(ServerRequest.SONG_SHOW);
    }
}