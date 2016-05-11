import sql.DBInterface;
import storage.User;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Matthew on 5/10/2016.
 */
public class Driver {

    public static void main(String[] args) {
        try {
            DBInterface.addUser("matterickson915@gmail.com", "Matt", "1994-06-30");
            User matt = DBInterface.getUser("matterickson915@gmail.com");
            System.out.println(matt);
            DBInterface.removeUser("matterickson915@gmail.com");
        } catch (IOException | SQLException e) {
            System.err.println("Main error: " + e.getMessage());
        }
    }

}
