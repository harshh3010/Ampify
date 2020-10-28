package utilities;

public enum PlaylistType {

    CREATE_PLAYLIST("1"),
    FETCH_USER_PLAYLISTS("2"),
    ADD_SONG_TO_A_PLAYLIST("3"),
    FETCH_SONGS_OF_A_PLAYLIST("4"),
    DELETE_PLAYLIST("5"),
    ;
    PlaylistType(String s) {

        s.toString();
    }
}
