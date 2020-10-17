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

    // USER DETAILS TABLE
    public static final String USER_DETAILS_TABLE = "user_details";
    public static final String USER_DETAILS_COL_EMAIL = "email";
    public static final String USER_DETAILS_COL_ARTIST = "artist";
    public static final String USER_DETAILS_COL_LANGUAGE = "language";
    public static final String USER_DETAILS_COL_GENRE = "genre";
    public static final String USER_DETAILS_COL_LIKED = "likedSong";
    public static final String USER_DETAILS_COL_PLAYLIST = "playlist";
}
