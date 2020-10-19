package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class SongFetchRequest implements Serializable {

    private String type;

    public SongFetchRequest(String type) {
        this.type = type;
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