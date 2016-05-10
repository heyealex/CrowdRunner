package sql;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by Matthew on 5/10/2016.
 */
public class DBInterface {

    private static SQLConnection connection = null;

    public static void init() throws IOException, SQLException {
        verifyConnection();
    }

    private static void verifyConnection() throws IOException, SQLException {
        if (connection == null || connection.isClosed()) {
            connection = new SQLConnection();
        }
    }

    public static void addUser(String name, String birthDate) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "insert into user (name, birth_date, join_date) values (?, ?, ?);",
                name, birthDate, new Date(System.currentTimeMillis())
        );
    }

    public static void removeUser(String name) throws IOException, SQLException {
        verifyConnection();
        connection.executeUpdate(
                "delete from user where name = ?", name
        );
    }

}
