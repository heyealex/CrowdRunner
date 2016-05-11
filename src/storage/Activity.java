package storage;

import java.sql.Timestamp;

/**
 * Storage class for an activity
 *
 * Author: Matthew Erickson
 */
public class Activity {

    public int id;
    public int userID;
    public Timestamp startDate, finishDate;
    public int distance;
    public String activityType;

    public Activity(int id, int userID, Timestamp startDate, Timestamp finishDate, int distance, String activityType) {
        this.id = id;
        this.userID = userID;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.distance = distance;
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return String.format("Activity; id: %d; userID: %d; distance: %d; activityType: %s",
                                id, userID, distance, activityType);
    }

}
