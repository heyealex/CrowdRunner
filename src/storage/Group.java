package storage;

import java.sql.Date;

/**
 * Created by Matthew on 5/10/2016.
 * Storage class for a group
 */
public class Group {

    public int groupID;
    public String name;
    public Date joinDate;
    public int adminID;

    public Group(int groupID, String name, Date joinDate, int adminID) {
        this.groupID = groupID;
        this.name = name;
        this.joinDate = joinDate;
        this.adminID = adminID;
    }

}
