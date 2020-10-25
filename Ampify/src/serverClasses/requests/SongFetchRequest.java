package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class SongFetchRequest implements Serializable {

    private String type;//any kind of fetching request this is must to be known
    private int ID;
    private int offset;//any kind of fetching request this is must to be known
    private int rowcount;//any kind of fetching request this is must to be known
    private String email;

    /**
     * this constructor's purpose : when want to get details of last played song for a particular user
     * @param type
     */

    public SongFetchRequest(String type,String email) {
        this.type = type;
        this.email=email;
    }

    /**
     * this constructor invoked when we want to fetch recommended songs for user!!
     * @param type
     * @param email
     * @param offset
     * @param rowcount
     */
    public SongFetchRequest(String type,String email,int offset,int rowcount) {
        this.email=email;
        this.type=type;
        this.offset=offset;
        this.rowcount=rowcount;
    }

    /**
     * this constructor invoked when we want to fetch top songs based on rating !!
     */

    public SongFetchRequest(String type, int offset, int rowcount)
    {
        this.offset=offset;
        this.type = type;
        this.rowcount=rowcount;
    }


    //when u want to fetch songs of particular id(whether artist or album )
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
    public String getEmail() {
        return email;
    }

    public int getRowcount() {
        return rowcount;
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