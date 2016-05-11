package sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Simple interface for accessing the database
 *
 * Author: Matthew Erickson
 */
public class SQLConnection {

    public static final String DEFAULT_CONFIG = "ConnectionProperties.txt";
    private static final String DB_URL = "dbUrl";

    private Properties properties = new Properties();
    private Connection connection;

    public SQLConnection() throws IOException, SQLException {
        this(DEFAULT_CONFIG);
    }

    public SQLConnection(String configFileName) throws IOException, SQLException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(configFileName);
        if (stream == null) {
            stream = new FileInputStream(DEFAULT_CONFIG);
        }
        properties.load(stream);
        connect();
    }

    private void connect() throws SQLException {
        String databaseUrl = properties.getProperty(DB_URL);
        connection = DriverManager.getConnection(databaseUrl, properties);
    }

    public ResultSet executeQuery(String query, Object... args) throws SQLException {
        if (args.length != countChars(query, '?')) {
            System.err.println("Wrong number of arguments to executeQuery");
            return null;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        //statement.setQueryTimeout(1);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                statement.setString(i + 1, (String) args[i]);
            } else if (args[i] instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) args[i]);
            } else if (args[i] instanceof Integer) {
                statement.setInt(i + 1, (Integer) args[i]);
            } else if (args[i] instanceof Date) {
                statement.setDate(i + 1, (Date) args[i]);
            }
        }
        return statement.executeQuery();
    }

    public boolean executeUpdate(String update, Object... args) throws SQLException {
        if (args.length != countChars(update, '?')) {
            System.err.println("Wrong number of arguments to executeUpdate");
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(update);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                statement.setString(i + 1, (String) args[i]);
            } else if (args[i] instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) args[i]);
            } else if (args[i] instanceof Integer) {
                statement.setInt(i + 1, (Integer) args[i]);
            } else if (args[i] instanceof Date) {
                statement.setDate(i + 1, (Date) args[i]);
            }
        }
        statement.executeUpdate();
        return true;
    }

    private int countChars(String query, char c) {
        int count = 0;
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    public boolean isClosed() {
        try {
            return !connection.isValid(1);
        } catch (SQLException e) {
            return true;
        }
    }

    public void close() {
        try {
            connection.close();
            properties = null;
        } catch (SQLException e) {
            System.err.println("Close error: " + e.getMessage());
        }
    }

    public void restart() {
        try {
            connect();
        } catch (SQLException e) {
            System.err.println("Restart error: " + e.getMessage());
        }
    }

}
