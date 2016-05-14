import sql.DBInterface;
import storage.Crowd;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * TestDriver class
 *
 * Author: Matthew Erickson
 */
public class TestDriver {

    public static void main(String[] args) {
        DBInterface.init();
        testScenario1();
    }

    private static void testScenario1() {
        String adminEmail = "adminUser@test.com";
        String user1Email = "user1@test.com";
        String user2Email = "user2@test.com";

        long now = System.currentTimeMillis();
        Timestamp startTime = new Timestamp(now);
        Timestamp endTime = new Timestamp(now + 1000*60*30);

        try {
            /* setup admin */
            DBInterface.addUser(adminEmail, "Admin", "1600-01-01");
            Crowd crowd1 = makeAdmin("Crowd1", adminEmail);
            Crowd crowd2 = makeAdmin("Crowd2", adminEmail);
            Crowd crowd3 = makeAdmin("Crowd3", adminEmail);
            System.out.println("Admin setup: Pass");

            /* add user1 to admin crowd */
            DBInterface.addUser(user1Email, "User1", "1700-01-01");
            DBInterface.userJoinCrowd(user1Email, crowd1);
            DBInterface.userJoinCrowd(user1Email, crowd2);
            System.out.println("User1 setup: Pass");

            /* add user 2 to admin crowd */
            DBInterface.addUser(user2Email, "User2", "1800-01-01");
            DBInterface.userJoinCrowd(user2Email, crowd2);
            DBInterface.userJoinCrowd(user2Email, crowd3);
            System.out.println("User2 setup: Pass");

            /* add activities */
            DBInterface.addActivity(adminEmail, startTime, endTime, 4500, "Running");
            DBInterface.addActivity(user1Email, startTime, endTime, 3500, "Running");
            DBInterface.addActivity(user2Email, startTime, endTime, 2500, "Running");
            System.out.println("Added activities: Pass");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static Crowd makeAdmin(String crowdName, String adminEmail) throws IOException, SQLException {
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        Date endDate = new Date(now + (1000*60*60*24*7));

        DBInterface.addCrowd(crowdName, adminEmail);
        Crowd adminCrowd = DBInterface.getCrowd(crowdName, adminEmail);
        DBInterface.addCrowdChallenge(adminCrowd.id, "AdminChallenge1", startDate, endDate, 12000);
        return adminCrowd;
    }

}
