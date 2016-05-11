package sql;

import storage.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matthew on 5/10/2016.
 */
public class DBInterface {

    /**
     * Connection object used for accessing database
     */
    private static SQLConnection connection = null;

    /**
     * Initialize connection (optional)
     * @throws IOException
     * @throws SQLException
     */
    public static void init() throws IOException, SQLException {
        verifyConnection();
    }

    /**
     * Re-establish connection if it isn't valid
     * @throws IOException
     * @throws SQLException
     */
    private static void verifyConnection() throws IOException, SQLException {
        if (connection == null || connection.isClosed()) {
            connection = new SQLConnection();
        }
    }

    /**
     * Add user to database
     * @param email
     * @param name
     * @param birthDate
     * @throws IOException
     * @throws SQLException
     */
    public static void addUser(String email, String name, String birthDate) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "insert into user (email, name, birth_date, join_date) values (?, ?, ?, ?);",
                email, name, birthDate, new Date(System.currentTimeMillis())
        );
    }

    /**
     * Remove user from database
     * @param email
     * @throws IOException
     * @throws SQLException
     */
    public static void removeUser(String email) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "delete from user where email = ?", email
        );
    }

    /**
     * Retrieve User from database. Returns null if user doesn't exist
     * @param email
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static User getUser(String email) throws IOException, SQLException {
        verifyConnection();
        ResultSet rs = connection.executeQuery(
                "select * from user where email = ?", email
        );
        if (rs.next()) {
            return new User(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
                    rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
        }
        return null;
    }

}
