package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class ArtistFetchRequest implements Serializable {

    String type;

    public ArtistFetchRequest(String type) {
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
        return String.valueOf(ServerRequest.ARTIST_SHOW);
    }
}
