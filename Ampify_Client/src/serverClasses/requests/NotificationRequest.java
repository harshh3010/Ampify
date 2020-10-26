package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class NotificationRequest implements Serializable {

    private String type;
    private String sender;
    private String receiver;
    private int playlistID;

    /**
     * this constructor will be used for sending notifications
     * @param type
     * @param sender
     * @param receiver
     * @param playlistID
     */
    public NotificationRequest(String type, String sender, String receiver, int playlistID) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.playlistID = playlistID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }
    @Override
    public String toString() {
        return String.valueOf(ServerRequest.NOTIFICATION_REQUEST);
    }

}
