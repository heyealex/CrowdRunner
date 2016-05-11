package sql;

import storage.Challenge;
import storage.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Contains functions that manipulate and query the database
 *
 * Author: Matthew Erickson
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
     * @return User
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

    /**
     * Add challenge to database
     * @param title
     * @param startDate
     * @param endDate
     * @param totalDistance
     * @throws IOException
     * @throws SQLException
     */
    public static void addChallenge(String title, Date startDate, Date endDate, int totalDistance) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "insert into challenge (title, start_date, end_date, completed_distance, total_distance) values (?, ?, ?, ?, ?);",
                title, startDate, endDate, 0, totalDistance
        );
    }

    /**
     * Remove challenge from database
     * @param title
     * @param start
     * @throws IOException
     * @throws SQLException
     */
    public static void removeChallenge(String title, Date start) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "delete from challenge where title = ? and start_date = ?", title, start
        );
    }

    /**
     * Retrieve challenge from database. Return null if challenge doesn't exist
     * @param title
     * @param start
     * @return Challenge
     * @throws IOException
     * @throws SQLException
     */
    public static Challenge getChallenge(String title, Date start) throws IOException, SQLException {
        verifyConnection();
        ResultSet rs = connection.executeQuery(
                "select * from challenge where title = ? and start_date = ?", title, start
        );
        if (rs.next()) {
            return new Challenge(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
                    rs.getInt(5), rs.getInt(6));
        }
        return null;
    }



}
