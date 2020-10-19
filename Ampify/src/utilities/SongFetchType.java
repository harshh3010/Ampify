package utilities;

public enum SongFetchType {


    TOP("0"),
    RECOMMENDED_SONGS("1"),
    SONGS_OF_PARTICULAR_ARTIST("3"),
    ;

    SongFetchType(String s) {
        s.toString();
    }
}
