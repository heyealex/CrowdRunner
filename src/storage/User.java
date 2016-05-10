package storage;

import java.sql.Date;

/**
 * Created by Matthew on 5/10/2016.
 * Storage class for a user
 */
public class User {

    public int userID;
    public String name;
    public Date birthDate, joinDate;
    public int selfChallenge;
    public int group1ID, group2ID, group3ID;

    public User(int userID, String name, Date birthDate, Date joinDate, int selfChallenge,
                int group1ID, int group2ID, int group3ID) {
        this.userID = userID;
        this.name = name;
        this.birthDate = birthDate;
        this.joinDate = joinDate;
        this.selfChallenge = selfChallenge;
        this.group1ID = group1ID;
        this.group2ID = group2ID;
        this.group3ID = group3ID;
    }

}
