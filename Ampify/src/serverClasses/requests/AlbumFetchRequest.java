package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class AlbumFetchRequest implements Serializable {

    String type;

    public AlbumFetchRequest(String type) {
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
        return String.valueOf(ServerRequest.ALBUM_SHOW);
    }
}