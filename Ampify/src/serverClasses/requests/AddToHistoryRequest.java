package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;
import java.sql.Timestamp;

public class AddToHistoryRequest implements Serializable {

    private int songId;
    private String userEmail;
    private Timestamp timestamp;

    public AddToHistoryRequest() {
    }
    public AddToHistoryRequest(int songId, String userEmail, Timestamp timestamp) {
        this.songId = songId;
        this.userEmail = userEmail;
        this.timestamp = timestamp;
    }

    public int getSongId() {

        return songId;
    }
    public void setSongId(int songId) {

        this.songId = songId;
    }
    public String getUserEmail() {

        return userEmail;
    }
    public void setUserEmail(String userEmail) {

        this.userEmail = userEmail;
    }
    public Timestamp getTimestamp() {

        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {

        this.timestamp = timestamp;
    }

    @Override
    public String toString() {

        return String.valueOf(ServerRequest.UPDATE_HISTORY);
    }
}
