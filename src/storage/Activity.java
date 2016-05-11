package storage;

import java.sql.Timestamp;

/**
 * Created by Matthew on 5/10/2016.
 * Storage class for an activity
 */
public class Activity {

    public int userID;
    public Timestamp startDate, finishDate;
    public int distance;
    public String activityType;

    public Activity(int userID, Timestamp startDate, Timestamp finishDate, int distance, String activityType) {
        this.userID = userID;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.distance = distance;
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return String.format("Activity; id: %d; distance: %d; activityType: %s",
                                userID, distance, activityType);
    }

}
