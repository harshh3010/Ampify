package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;
import java.sql.Timestamp;

public class PlaySongRequest implements Serializable {
    private String userEmail;
    private int songID;
    private Timestamp timePlayed;
    private boolean is_playing=false;

    public PlaySongRequest() {
    }

    public boolean isIs_playing() {
        return is_playing;
    }

    public void setIs_playing(boolean is_playing) {
        this.is_playing = is_playing;
    }

    public PlaySongRequest(String userEmail, int songID, Timestamp timePlayed) {
        this.userEmail = userEmail;
        this.songID = songID;
        this.timePlayed = timePlayed;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public Timestamp getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Timestamp timePlayed) {
        this.timePlayed = timePlayed;
    }


    @Override
    public String toString() {
        return String.valueOf(ServerRequest.PLAY_SONG);
    }

}
