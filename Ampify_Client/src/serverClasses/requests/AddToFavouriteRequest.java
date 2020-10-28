package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class AddToFavouriteRequest implements Serializable {
    private int songID;
    private String email;

    public AddToFavouriteRequest(int songID, String email) {
        this.songID = songID;
        this.email = email;
    }

    public int getSongID() {

        return songID;
    }

    public void setSongID(int songID) {

        this.songID = songID;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }
    @Override
    public String toString() {

        return String.valueOf(ServerRequest.ADD_TO_FAVOURITE);
    }

}
