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
    PLAY_SONG("9"),// TODO PLAY SONGS *_*
    UPDATE_HISTORY("10"),
    FETCH_USER_HISTORY("20"),
    PLAYLIST_REQUEST("21"),
    NOTIFICATION_REQUEST("22"),
    SEARCH_REQUEST("23"),
    ADD_TO_FAVOURITE("24"),
    GET_TOP_ARTISTS("11"),   // TODO
    GET_TOP_SONGS("12"),     // TODO
    GET_FAVOURITE_SONGS("13"),
    GET_PLAYLISTS("14"),
    GET_TRENDING_SONGS("15"),
    GET_TOP_ALBUMS("16"),       // TODO
    GET_RECOMMENDED_SONGS("17"),    //TODO
    GET_ALL_ALBUMS("18"),   //TODO
    ;

    ServerRequest(String s) {
        s.toString();
    }
}