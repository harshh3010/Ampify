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
}
