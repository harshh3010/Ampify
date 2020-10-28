package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class PlaylistRequest implements Serializable {
    private String playlistName;
    private String email;
    private String privacy;
    private String type;
    private int songId;
    private int playlistId;
    private String category;        //tells whether it is group or user's

    /**
     * this constructor will be invoked when we will create a playlist
     * @param playlistName
     * @param email
     * @param privacy
     * @param type
     */
    public PlaylistRequest(String type,String playlistName, String email, String privacy, String category) {
        this.playlistName = playlistName;
        this.email = email;
        this.privacy = privacy;
        this.type = type;
        this.category=category;
    }

    /**
     * this constructor :: when u want to fetch all playlists belonging to a particular user!!
     * @param email
     */
    public PlaylistRequest(String type,String email) {
        this.type=type;
        this.email = email;
    }
    /**
     * this constructor :: when u want to delete a playlist!!
     * @param email
     */
    public PlaylistRequest(String type,String email,int playlistId) {
        this.type=type;
        this.email = email;
        this.playlistId = playlistId;
    }
    /**
     * use:: when a particular song id to be added to a particular playlist
     * as requested by the client
     * @param songId
     * @param playlistId
     */
    public PlaylistRequest(String type, int playlistId,int songId) {
        this.songId = songId;
        this.type=type;
        this.playlistId = playlistId;
    }
    /**
     * whenn request is to fetch all songs of a particular playlist
     * user email not reqd here but playlistId
     * coz user may demand to view songs of any playlist(obvio if public n )
     * so then wont create other function for it
     * this alone will sere the task whether to fetch songs of his own playlist
     * or anyone's else
     * ek teer s do nishane !!
     * @param playlistId
     */
    public PlaylistRequest(String type,int playlistId) {
        this.type=type;
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {

        return playlistName;
    }
    public int getSongId() {

        return songId;
    }
    public void setSongId(int songId) {

        this.songId = songId;
    }
    public int getPlaylistId() {

        return playlistId;
    }
    public void setPlaylistId(int playlistId) {

        this.playlistId = playlistId;
    }
    public String getCategory() {

        return category;
    }
    public void setCategory(String category) {

        this.category = category;
    }
    public void setPlaylistName(String playlistName) {

        this.playlistName = playlistName;
    }
    public String getEmail() {

        return email;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public String getPrivacy() {

        return privacy;
    }
    public void setPrivacy(String privacy) {

        this.privacy = privacy;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {

        this.type = type;
    }

    @Override
    public String toString() {

        return String.valueOf(ServerRequest.PLAYLIST_REQUEST);
    }

}
