package storage;

import java.sql.Date;

/**
 * Storage class for a challenge
 *
 * Author: Matthew Erickson
 */
public class Challenge {

    public int id;
    public String title;
    public Date startDate, endDate;
    public int totalDistance, completedDistance;

    public Challenge(int id, String title, Date startDate, Date endDate,
                     int totalDistance, int completedDistance) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDistance = totalDistance;
        this.completedDistance = completedDistance;
    }

    @Override
    public String toString() {
        return String.format("Challenge; id: %d; title: %s; totalDistance: %d; completedDistance: %d",
                id, title, totalDistance, completedDistance);
    }

}
