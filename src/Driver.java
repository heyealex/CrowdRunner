import sql.DBInterface;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Matthew on 5/10/2016.
 */
public class Driver {

    public static void main(String[] args) {
        try {
            DBInterface.addUser("Matt", "1994-06-30");
            //DBInterface.removeUser("Matt");
        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
