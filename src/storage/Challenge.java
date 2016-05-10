package storage;

import java.sql.Timestamp;

/**
 * Created by Matthew on 5/10/2016.
 * Storage class for a challenge
 */
public class Challenge {

    public int challengeID;
    public String title;
    public Timestamp startDate, endDate;
    public int totalDistance, completedDistance;

    public Challenge(int challengeID, String title, Timestamp startDate, Timestamp endDate,
                     int totalDistance, int completedDistance) {
        this.challengeID = challengeID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDistance = totalDistance;
        this.completedDistance = completedDistance;
    }

}
