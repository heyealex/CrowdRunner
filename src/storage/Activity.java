package storage;

import java.sql.Timestamp;

/**
 * Storage class for an activity
 *
 * Author: Matthew Erickson
 */
public class Activity {

    public int id;
    public String userEmail;
    public Timestamp startDate, finishDate;
    public int distance;
    public String activityType;

    public Activity(int id, String userEmail, Timestamp startDate, Timestamp finishDate, int distance, String activityType) {
        this.id = id;
        this.userEmail = userEmail;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.distance = distance;
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return String.format("Activity; id: %d; userEmail: %s; distance: %d; activityType: %s",
                                id, userEmail, distance, activityType);
    }

}
