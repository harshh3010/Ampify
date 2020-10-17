package utilities;

public enum ServerRequest {

    SIGNUP_REQUEST("0"),
    LOGIN_REQUEST("1"),
    LANGUAGE_SHOW("3"),
    GENRES_SHOW("4"),
    ARTIST_SHOW("5"),
    SUBMIT_CHOICES("6"),
    GET_CHOICES("7"),       //TODO
    GET_TOP_ARTISTS("8"),   // TODO
    GET_TOP_SONGS("9"),     // TODO
    GET_FAVOURITE_SONGS("10"),
    GET_TRENDING_SONGS("12"),
    GET_PLAYLISTS("11"),
    GET_TOP_ALBUMS("13"),       // TODO
    GET_RECOMMENDED_SONGS("14"),    //TODO
    GET_ALL_ALBUMS("15"),   //TODO
    ;

    ServerRequest(String s) {
        s.toString();
    }
}