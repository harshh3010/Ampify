package serverClasses.requests;

public enum SongListType {

    RECENTLY_ADDED_SONGS("0"),
    RECOMMENDED_SONGS("1"),
    TOP_SONGS("2"),
    ;

    SongListType(String s){
        s.toString();
    }

}
