package storage;

import java.sql.Date;

/**
 * Storage class for a user challenge
 *
 * Author: Matthew Erickson
 */
public class UserChallenge {

    public int id;
    public String userEmail, title;
    public Date startDate, endDate;
    public int totalDistance, completedDistance;

    public UserChallenge(int id, String userEmail, String title, Date startDate, Date endDate,
                         int completedDistance, int totalDistance) {
        this.id = id;
        this.userEmail = userEmail;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completedDistance = completedDistance;
        this.totalDistance = totalDistance;
    }

    @Override
    public String toString() {
        return String.format("UserChallenge; Title: %s; Start: %s; totalDistance: %d; completedDistance: %d",
                title, startDate.toLocalDate(), totalDistance, completedDistance);
    }

}
