package storage;

import java.sql.Date;

/**
 * Created by Matthew on 5/10/2016.
 * Storage class for a group
 */
public class Crowd {

    public int groupID;
    public String name;
    public Date joinDate;
    public int adminID;

    public Crowd(int groupID, String name, Date joinDate, int adminID) {
        this.groupID = groupID;
        this.name = name;
        this.joinDate = joinDate;
        this.adminID = adminID;
    }

    @Override
    public String toString() {
        return String.format("Crowd; id: %d; name: %s; admin: %d", groupID, name, adminID);
    }

}
