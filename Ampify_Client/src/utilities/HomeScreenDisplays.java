/*
Enum for the pane being displayed in home screen's display pane
*/

package utilities;

public enum HomeScreenDisplays {

    MAIN_PAGE("0"),
    HISTORY_PAGE("1"),
    ARTIST_PAGE("2"),
    SONG_LIST_PAGE("3"),
    PLAYLIST_SCREEN("4"),
    DOWNLOADS_SCREEN("5"),
    ALBUM_PAGE("6"),
    LOCAL_MUSIC_SCREEN("7"),
    SEARCH_PAGE("8"),
    ;

    HomeScreenDisplays(String s) {
        s.toString();
    }
}
