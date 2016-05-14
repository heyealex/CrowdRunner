package sql;

import storage.*;

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
    public static SQLConnection connection = null;

    /**
     * Initialize connection (optional)
     * @throws IOException
     * @throws SQLException
     */
    public static void init() {
        try {
            verifyConnection();
        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Re-establish connection if it isn't valid
     * @throws IOException
     * @throws SQLException
     */
    private static void verifyConnection() throws IOException, SQLException {
        if (connection == null) {
            connection = new SQLConnection();
        }
        /*
        else if (connection.isClosed()) {
            connection.restart();
        }*/
    }

    /**
     * Add User to database
     * @param email
     * @param name
     * @param birthDate
     * @throws IOException
     * @throws SQLException
     */
    public static void addUser(String email, String name, String birthDate) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "insert into User (email, name, birth_date, join_date) values (?, ?, ?, ?);",
                email, name, birthDate, new Date(System.currentTimeMillis())
        );
    }

    /**
     * Remove User from database
     * @param email
     * @throws IOException
     * @throws SQLException
     */
    public static void removeUser(String email) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "delete from User where email = ?", email
        );
    }

    /**
     * Retrieve User from database. Returns null if User doesn't exist
     * @param email
     * @return User
     * @throws IOException
     * @throws SQLException
     */
    public static User getUser(String email) throws IOException, SQLException {
        verifyConnection();
        ResultSet rs = connection.executeQuery(
                "select * from User where email = ?", email
        );
        if (rs.next()) {
            return new User(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
                    rs.getInt(5), rs.getInt(6), rs.getInt(7));
        }
        return null;
    }

    /**
     * Add Crowd to database if user has a free crowd slot
     * Set free crowd slot for this user if successful
     * @param name
     * @param adminEmail
     * @throws IOException
     * @throws SQLException
     */
    public static void addCrowd(String name, String adminEmail) throws IOException, SQLException {
        verifyConnection();
        User user = getUser(adminEmail);
        if (user == null) {
            System.err.println("User " + adminEmail + " doesn't exist");
            return;
        }

        String freeCrowdSlot = findFreeSlot(user, -1);
        if (freeCrowdSlot == null) {
            System.err.println("No free crowd slot for " + adminEmail);
            return;
        }

        connection.executeUpdate(
                "insert into Crowd (name, join_date, admin_id) values (?, ?, ?)",
                name, new Date(System.currentTimeMillis()), adminEmail
        );
        Crowd crowd = getCrowd(name, adminEmail);
        if (crowd == null) {
            System.err.println("Crowd " + name + " not added");
            return;
        }
        connection.executeUpdate(
                "update User set " + freeCrowdSlot + " = ? where email = ?",
                crowd.id, adminEmail
        );
    }

    /**
     * Remove Crowd from database
     * @param name
     * @param adminEmail
     * @throws IOException
     * @throws SQLException
     */
    public static void removeCrowd(String name, String adminEmail) throws IOException, SQLException {
        verifyConnection();
        Crowd crowd = getCrowd(name, adminEmail);
        if (crowd == null) return;

        /* clear crowd slot 1 for all users with this crowd id */
        connection.executeUpdate(
                "update User set crowd1_id = 0 where crowd1_id = ?", crowd.id
        );

        /* clear crowd slot 2 for all users with this crowd id */
        connection.executeUpdate(
                "update User set crowd2_id = 0 where crowd2_id = ?", crowd.id
        );

        /* clear crowd slot 3 for all users with this crowd id */
        connection.executeUpdate(
                "update User set crowd3_id = 0 where crowd3_id = ?", crowd.id
        );

        connection.executeUpdate(
                "delete from Crowd where id = ?", crowd.id
        );
    }

    /**
     * Retrieve Crowd from database. Returns null if Crowd doesn't exist
     * @param name
     * @param adminEmail
     * @return Crowd
     * @throws IOException
     * @throws SQLException
     */
    public static Crowd getCrowd(String name, String adminEmail) throws IOException, SQLException {
        verifyConnection();
        ResultSet rs = connection.executeQuery(
                "select * from Crowd where name = ? and admin_id = ?", name, adminEmail
        );
        if (rs.next()) {
            return new Crowd(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4));
        }
        return null;
    }

    /**
     * Retrieve Crowd from database. Returns null if Crowd doesn't exist
     * @param crowdID
     * @return Crowd
     * @throws IOException
     * @throws SQLException
     */
    public static Crowd getCrowd(int crowdID) throws IOException, SQLException {
        verifyConnection();
        ResultSet rs = connection.executeQuery(
                "select * from Crowd where id = ?", crowdID
        );
        if (rs.next()) {
            return new Crowd(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(5));
        }
        return null;
    }

    /**
     * Add Activity to database
     * @param userEmail
     * @param startDate
     * @param finishDate
     * @param distance
     * @param activityType
     * @throws IOException
     * @throws SQLException
     */
    public static void addActivity(String userEmail, Timestamp startDate, Timestamp finishDate, int distance, String activityType) throws IOException, SQLException {
        verifyConnection();
        User user = getUser(userEmail);
        if (user == null) {
            System.err.println("Cannot add activity for non-existent user");
            return;
        }
        connection.executeUpdate(
                "insert into Activity (user_email, start_date, finish_date, distance, activity_type) value (?, ?, ?, ?, ?)",
                userEmail, startDate, finishDate, distance, activityType
        );

        /* Apply activity benefit to personal challenge */
        applyUserBenefit(userEmail, distance);

        /* Apply activity benefit to crowds */
        applyCrowdBenefit(user.crowd1ID, distance);
        applyCrowdBenefit(user.crowd2ID, distance);
        applyCrowdBenefit(user.crowd3ID, distance);
    }

    /**
     * Remove Activity from database
     * @param id
     * @throws IOException
     * @throws SQLException
     */
    public static void removeActivity(int id) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "delete from Activity where id = ?", id
        );
    }

    /**
     * Retrieve Activity from database
     * @param id
     * @return Activity
     * @throws IOException
     * @throws SQLException
     */
    public static Activity getActivity(int id) throws IOException, SQLException {
        verifyConnection();
        ResultSet rs = connection.executeQuery(
                "select * from Activity where id = ?", id
        );
        if (rs.next()) {
            return new Activity(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4), rs.getInt(5), rs.getString(6));
        }
        return null;
    }

    /**
     * Add CrowdChallenge to the database
     * @param crowdID
     * @param title
     * @param startDate
     * @param endDate
     * @param totalDistance
     * @throws IOException
     * @throws SQLException
     */
    public static void addCrowdChallenge(int crowdID, String title, Date startDate, Date endDate, int totalDistance) throws IOException, SQLException {
        verifyConnection();
        CrowdChallenge challenge = getCrowdChallenge(crowdID);
        if (challenge != null) {
            /* challenge already exists */
            return;
        }
        connection.executeUpdate(
                "insert into CrowdChallenge (crowd_id, title, start_date, end_date, completed_distance, total_distance) values (?,?,?,?,?,?)",
                crowdID, title, startDate, endDate, 0, totalDistance
        );
    }

    /**
     * Remove CrowdChallenge from database
     * @param id
     * @throws IOException
     * @throws SQLException
     */
    public static void removeCrowdChallenge(int id) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "delete from CrowdChallenge where id = ?", id
        );
    }

    /**
     * Retrieve CrowdChallenge from database
     * @param crowdID
     * @return CrowdChallenge
     * @throws IOException
     * @throws SQLException
     */
    public static CrowdChallenge getCrowdChallenge(int crowdID) throws IOException, SQLException {
        verifyConnection();
        Date now = new Date(System.currentTimeMillis());
        ResultSet rs = connection.executeQuery(
                "select * from CrowdChallenge where crowd_id = ? and ? between start_date and end_date", crowdID, now
        );
        if (rs.next()) {
            return new CrowdChallenge(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4),
                    rs.getDate(5), rs.getInt(6), rs.getInt(7));
        }
        return null;
    }

    /**
     * Add UserChallenge to database
     * @param userEmail
     * @param title
     * @param startDate
     * @param endDate
     * @param totalDistance
     * @throws IOException
     * @throws SQLException
     */
    public static void addUserChallenge(String userEmail, String title, Date startDate, Date endDate, int totalDistance) throws IOException, SQLException {
        verifyConnection();
        UserChallenge challenge = getUserChallenge(userEmail);
        if (challenge != null) {
            /* challenge already exists */
            return;
        }
        connection.executeUpdate(
                "insert into UserChallenge (user_email, title, start_date, end_date, completed_distance, total_distance) values (?,?,?,?,?,?)",
                userEmail, title, startDate, endDate, 0, totalDistance
        );
    }

    /**
     * Remove UserChallenge from database
     * @param id
     * @throws IOException
     * @throws SQLException
     */
    public static void removeUserChallenge(int id) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "delete from UserChallenge where id = ?", id
        );
    }

    /**
     * Retrieve UserChallenge from database
     * @param userEmail
     * @return UserChallenge
     * @throws IOException
     * @throws SQLException
     */
    public static UserChallenge getUserChallenge(String userEmail) throws IOException, SQLException {
        verifyConnection();
        Date now = new Date(System.currentTimeMillis());
        ResultSet rs = connection.executeQuery(
                "select * from UserChallenge where user_email = ? and ? between start_date and end_date", userEmail, now
        );
        if (rs.next()) {
            return new UserChallenge(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4),
                    rs.getDate(5), rs.getInt(6), rs.getInt(7));
        }
        return null;
    }

    /**
     * Returns the column name for the given User in which a new Crowd can be added
     * @param user
     * @param id
     * @return
     */
    private static String findFreeSlot(User user, int id) {
        if (user.crowd1ID == id || user.crowd2ID == id || user.crowd3ID == id) {
            return null;
        }
        return (user.crowd1ID == 0 ? "crowd1_id" : (user.crowd2ID == 0 ? "crowd2_id" :
                (user.crowd3ID == 0 ? "crowd3_id" : null)));
    }

    /**
     * Adds Activity distance towards a CrowdChallenge
     * @param crowdID
     * @param distance
     * @throws IOException
     * @throws SQLException
     */
    private static void applyCrowdBenefit(int crowdID, int distance) throws IOException, SQLException {
        CrowdChallenge challenge = getCrowdChallenge(crowdID);
        if (challenge != null) {
            connection.executeUpdate(
                    "update CrowdChallenge set completed_distance = completed_distance + ? where id = ?", distance, challenge.id
            );
        }
    }

    /**
     * Adds Activity distance towards a CrowdChallenge
     * @param userEmail
     * @param distance
     * @throws IOException
     * @throws SQLException
     */
    private static void applyUserBenefit(String userEmail, int distance) throws IOException, SQLException {
        UserChallenge challenge = getUserChallenge(userEmail);
        if (challenge != null) {
            connection.executeUpdate(
                    "update UserChallenge set completed_distance = completed_distance + ? where user_email = ?", distance, challenge.userEmail
            );
        }
    }

    /**
     * For the provided User, attempt to join the given Crowd
     * @param userEmail
     * @param crowd
     * @throws IOException
     * @throws SQLException
     */
    public static void userJoinCrowd(String userEmail, Crowd crowd) throws IOException, SQLException {
        verifyConnection();
        User user = getUser(userEmail);
        if (user == null) {
            System.err.println("User doesn't exist");
            return;
        }
        String freeCrowdSlot = findFreeSlot(user, crowd.id);
        if (freeCrowdSlot == null) {
            System.err.println("No free crowd slot for " + userEmail);
        } else {
            connection.executeUpdate(
                    "update User set " + freeCrowdSlot + " = ? where email = ?",
                    crowd.id, userEmail
            );
        }
    }

}