package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class SongFetchRequest implements Serializable {

    private String type;
    private int ID;
    private int offset;
    private int rowcount;

    public int getRowcount() {
        return rowcount;
    }

    public SongFetchRequest(String type, int offset, int rowcount)
    {
        this.offset=offset;
        this.type = type;
        this.rowcount=rowcount;
    }


    //when u want to fetch songs of particular id
    //TODO Particulr ALBUM ID SONGS CAN ALSO BE ACCESSED THRU THIS CONSTRUCTOR *_*
    public SongFetchRequest(String type, int ID,int offset,int rowcount) {
        this.type = type;
        this.ID=ID;
        this.offset=offset;
        this.rowcount=rowcount;
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {

        return String.valueOf(ServerRequest.SONG_SHOW);
    }
}