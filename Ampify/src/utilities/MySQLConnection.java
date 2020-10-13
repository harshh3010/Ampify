package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for connection of
 * Mysql Database
 */
public class MySQLConnection {

    /**
     * Method for creating connection between
     * Mysql Database and Java Code
     *
     * @return Connection Object , null if Connection not Established
     */
    public static Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(DatabaseConstants.HOST,DatabaseConstants.DATABASE_USERNAME, DatabaseConstants.DATABASE_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
