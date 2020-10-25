package utilities;

public enum SongFetchType {


    TOP("0"),
    SONGS_OF_USER_CHOICE("1"),
    SONGS_OF_PARTICULAR_ARTIST("3"),
    SONGS_OF_PARTICULAR_ALBUM("4"),
    RECENT_SONGS("5"),
    ;

    SongFetchType(String s) {
        s.toString();
    }
}
