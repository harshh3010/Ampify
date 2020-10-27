package utilities;

public enum SongFetchType {

    TOP("0"),
    SONGS_OF_USER_CHOICE("1"),
    SONGS_OF_PARTICULAR_ARTIST("3"),
    SONGS_OF_PARTICULAR_ALBUM("4"),
    RECENT_ADDED_SONGS("5"),
    LAST_PLAYED_SONG("6"),
    RECENT_PLAYED_SONGS_BY_USER("7"),
    MOST_PLAYED_SONGS_BY_USER("8"),
    ;

    SongFetchType(String s) {
        s.toString();
    }
}
