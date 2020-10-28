package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserHistory implements Serializable {
    private int songId;
    private String songName;
    private Timestamp timePlayed;
    private int numberOfTimesPlayed;

    public int getSongId() {

        return songId;
    }
    public void setSongId(int songId) {

        this.songId = songId;
    }
    public String getSongName() {

        return songName;
    }
    public void setSongName(String songName) {

        this.songName = songName;
    }
    public Timestamp getTimePlayed() {

        return timePlayed;
    }
    public void setTimePlayed(Timestamp timePlayed) {

        this.timePlayed = timePlayed;
    }
    public int getNumberOfTimesPlayed() {

        return numberOfTimesPlayed;
    }
    public void setNumberOfTimesPlayed(int numberOfTimesPlayed) {

        this.numberOfTimesPlayed = numberOfTimesPlayed;
    }
}
