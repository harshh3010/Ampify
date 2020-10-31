package utilities;

public enum ServerRequest {

    SIGNUP_REQUEST("0"),
    LOGIN_REQUEST("1"),
    LANGUAGE_SHOW("2"),
    GENRES_SHOW("3"),
    ARTIST_SHOW("4"),
    SUBMIT_CHOICES("5"),
    GET_CHOICES("6"),
    SONG_SHOW("7"),
    ALBUM_SHOW("8"),
    UPDATE_HISTORY("9"),
    FETCH_USER_HISTORY("10"),
    PLAYLIST_REQUEST("11"),
    NOTIFICATION_REQUEST("12"),
    SEARCH_REQUEST("13"),
    ADD_TO_FAVOURITE("14"),
    ;

    ServerRequest(String s) {
        s.toString();
    }
}