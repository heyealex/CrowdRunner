package storage;

import java.sql.Date;

/**
 * Storage class for a crowd challenge
 *
 * Author: Matthew Erickson
 */
public class CrowdChallenge {

    public int id, crowdID;
    public String title;
    public Date startDate, endDate;
    public int totalDistance, completedDistance;

    public CrowdChallenge(int id, int crowdID, String title, Date startDate, Date endDate,
                         int completedDistance, int totalDistance) {
        this.id = id;
        this.crowdID = crowdID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completedDistance = completedDistance;
        this.totalDistance = totalDistance;
    }

    @Override
    public String toString() {
        return String.format("CrowdChallenge; Title: %s; Start: %s; totalDistance: %d; completedDistance: %d",
                title, startDate.toLocalDate(), totalDistance, completedDistance);
    }

}
