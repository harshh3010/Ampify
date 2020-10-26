package utilities;

public class DatabaseConstants {

    // MYSQL DATABASE URL AND CREDENTIALS
    public static final String HOST = "jdbc:mysql://www.db4free.net:3306/softablitz2020";
    public static final String DATABASE_USERNAME = "softablitz_2020";
    public static final String DATABASE_PASS = "password";

    // USER AUTHENTICATION TABLE
    public  static final String AUTH_TABLE_NAME = "user_auth";
    public static final String AUTH_TABLE_COL_EMAIL = "email";
    public static final String AUTH_TABLE_COL_PASS = "password";

    // ARTIST TABLE
    public static final String ARTIST_TABLE = "artist";
    public static final String ARTIST_COL_ID = "IDartist";
    public static final String ARTIST_COL_NAME = "artistName";
    public static final String ARTIST_COL_IMAGE = "artistImageURL";
    public static final String ARTIST_COL_RATING = "artistRating";

    // ALBUM TABLE
    public static final String ALBUM_TABLE = "albums";
    public static final String ALBUM_COL_ID = "IDalbum";
    public static final String ALBUM_COL_NAME = "albumName";
    public static final String ALBUM_COL_RELEASEDATE = "albumReleaseDate";
    public static final String ALBUM_COL_RATING = "albumRating";


    // LANGUAGE TABLE
    public static final String LANGUAGE_TABLE = "Language";
    public static final String LANGUAGE_COL_NAME = "languageName";

    // GENRE TABLE
    public static final String GENRES_TABLE = "Genres";
    public static final String GENRES_COL_NAME = "genresName";

    // ARTISTS CHOICE TABLE
    public static final String USER_CHOICE_ARTIST_TABLE = "userChoice_artist";
    public static final String USER_CHOICE_ARTIST_COL_EMAIL = "user_email";
    public static final String USER_CHOICE_ARTIST_COL_ARTIST_ID = "user_artistID";

    // LANGUAGES CHOICE TABLE
    public static final String USER_CHOICE_LANGUAGE_TABLE = "userChoice_language";
    public static final String USER_CHOICE_LANGUAGE_COL_EMAIL = "useremail";
    public static final String USER_CHOICE_LANGUAGE_COL_NAME = "user_language";

    // GENRES CHOICE TABLE
    public static final String USER_CHOICE_GENRES_TABLE = "userChoice_genres";
    public static final String USER_CHOICE_GENRES_COL_EMAIL = "user_email";
    public static final String USER_CHOICE_GENRES_COL_NAME = "user_genres";

    //SONG TABLE
    public static final String SONG_TABLE = "songs";
    public static final String SONG_COL_ID = "IDsong";
    public static final String SONG_COL_NAME = "songName";
    public static final String SONG_COL_ARTISTID = "IDartist";
    public static final String SONG_COL_LANGUAGE = "languages";
    public static final String SONG_COL_GENRES = "genre";
    public static final String SONG_COL_MUSIC_URL = "musicURL";
    public static final String SONG_COL_LYRICS_URL = "lyricsURL";
    public static final String SONG_COL_IMAGE_URL = "imageURL";
    public static final String SONG_COL_ALBUMID = "IDalbum";
    public static final String SONG_COL_RELEASE_DATE = "releaseDate";
    public static final String SONG_COL_RATING = "rating";

    //USER HISTORY TABLE
    public static final String USER_HISTORY_TABLE = "user_history";
    public static final String USER_HISTORY_COL_EMAIL = "user_email";
    public static final String USER_HISTORY_COL_SONGID = "song_id";
    public static final String USER_HISTORY_COL_TIMEPLAYED = "time_played";

    //PLAYLIST TABLE
    public static final String PLAYLIST_TABLE ="playlist";
    public static final String PLAYLIST_COL_ID = "id";
    public static final String PLAYLIST_COL_NAME = "playlist_name";
    public static final String PLAYLIST_COL_OWNER = "owner";
    public static final String PLAYLIST_COL_CREATED = "date_created";
    public static final String PLAYLIST_COL_PRIVACY = "privacy";
    public static final String PLAYLIST_COL_CATEGORY = "category";
    //playlist song table
    public static final String PLAYLIST_SONG_TABLE="songsOfPlaylist";
    public static final String PLAYLIST_SONG_COL_PLAYLIST_ID="playlistID";
    public static final String PLAYLIST_SONG_COL_SONG_ID="songID";



}
