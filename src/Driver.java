import sql.DBInterface;
import storage.User;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Driver test class
 *
 * Author: Matthew Erickson
 */
public class Driver {

    public static void main(String[] args) {
        testUserAdd();
    }

    private static void testUserAdd() {
        String testEmail = "user-" + System.nanoTime() + "@gmail.com";
        String name = "User";
        String birthDate = "1994-01-01";
        try {
            DBInterface.addUser(testEmail, name, birthDate);
            User user = DBInterface.getUser(testEmail);
            if (user != null) {
                System.out.println("Add User: Pass");
                DBInterface.removeUser(user.email);
            }
        } catch (IOException | SQLException e) {
            System.out.printf("Add User: Failed (%s)%n", e.getMessage());
        }
    }

}
