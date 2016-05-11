package storage;

import java.sql.Date;

/**
 * Storage class for a crowd (AKA Group but that's a reserved word in SQL)
 *
 * Author: Matthew Erickson
 */
public class Crowd {

    public String name;
    public Date joinDate;
    public int challengeID;
    public int adminID;

    public Crowd(String name, Date joinDate, int challengeID, int adminID) {
        this.name = name;
        this.joinDate = joinDate;
        this.challengeID = challengeID;
        this.adminID = adminID;
    }

    @Override
    public String toString() {
        return String.format("Crowd; name: %s; admin: %d; challenge: %d", name, adminID, challengeID);
    }

}
