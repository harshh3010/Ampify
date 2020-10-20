package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class SongFetchRequest implements Serializable {

    private String type;
    private int ID;

    public SongFetchRequest(String type) {
        this.type = type;
    }


    //when u want to fetch songs of particular id
    //TODO Particulr ALBUM ID SONGS CAN ALSO BE ACCESSED THRU THIS CONSTRUCTOR *_*
    public SongFetchRequest(String type, int ID) {
        this.type = type;
        this.ID=ID;
    }
    public int getID() {
        return ID;
    }

    public void setArtistID(int ID) {
        this.ID = ID;
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