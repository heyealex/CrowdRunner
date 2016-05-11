import sql.DBInterface;
import storage.Challenge;
import storage.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Driver test class
 *
 * Author: Matthew Erickson
 */
public class Driver {

    public static void main(String[] args) {
        testUserAdd();
        testChallengeAdd();
    }

    private static void testChallengeAdd() {
        long now = System.currentTimeMillis();
        String title = "challenge-" + now;
        Date start = new Date(now);
        Date end = new Date(now + 1000*60*60*24*7);
        int distance = 50;
        try {
            DBInterface.addChallenge(title, start, end, distance);
            Challenge challenge = DBInterface.getChallenge(title, start);
            if (challenge != null) {
                System.out.println("Add Challenge: Pass");
                DBInterface.removeChallenge(title, start);
                challenge = DBInterface.getChallenge(title, start);
                System.out.printf("Remove challenge: %s%n", (challenge == null ? "Pass" : "Failure"));
                return;
            }
            throw new SQLException("Not added");
        } catch (IOException | SQLException e) {
            System.out.printf("Add Challenge: Failed (%s)%n", e.getMessage());
        }
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
                user = DBInterface.getUser(testEmail);
                System.out.printf("Remove User: %s%n", (user == null ? "Pass" : "Failure"));
                return;
            }
            throw new SQLException("Not added");
        } catch (IOException | SQLException e) {
            System.out.printf("Add User: Failed (%s)%n", e.getMessage());
        }
    }

}
