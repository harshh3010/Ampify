package services;

import model.Artist;
import model.Genres;
import model.Language;
import serverClasses.Main;
import serverClasses.requests.SubmitChoicesRequest;
import utilities.DatabaseConstants;
import utilities.Status;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubmitChoices {

    public String saveChoices(SubmitChoicesRequest submitChoicesRequest) {

        // Saving user choices in the database

        try {
            // Query to insert data in user details table
            String query = "INSERT INTO " + DatabaseConstants.USER_DETAILS_TABLE +
                    "(" +
                    DatabaseConstants.USER_DETAILS_COL_EMAIL + "," +
                    DatabaseConstants.USER_DETAILS_COL_ARTIST + "," +
                    DatabaseConstants.USER_DETAILS_COL_LANGUAGE + "," +
                    DatabaseConstants.USER_DETAILS_COL_GENRE + "," +
                    DatabaseConstants.USER_DETAILS_COL_LIKED + "," +
                    DatabaseConstants.USER_DETAILS_COL_PLAYLIST +
                    ") VALUES(?,?,?,?,?,?);";

            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);

            // Looping through list to insert all records in database
            for (Language language : submitChoicesRequest.getSelectedLanguages()) {
                for (Genres genre : submitChoicesRequest.getSelectedGenres()) {
                    for (Artist artist : submitChoicesRequest.getSelectedArtists()) {

                        // Inserting values in prepared statement
                        preparedStatement.setString(1, submitChoicesRequest.getEmail());
                        preparedStatement.setInt(2, artist.getArtistID());
                        preparedStatement.setString(3, language.getLanguage());
                        preparedStatement.setString(4, genre.getGenres());
                        preparedStatement.setInt(5,0);
                        preparedStatement.setInt(6, 0);

                        // Adding statement to batch of statements to be executed
                        preparedStatement.addBatch();
                    }
                }
            }

            try {
                // Executing the prepared batch of statements and returning success result
                preparedStatement.executeBatch();
                return String.valueOf(Status.SUCCESS);
            } catch (SQLException e) {
                // Displaying error in case of failure
                System.out.println("Error message: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(Status.FAILED);
    }

}
