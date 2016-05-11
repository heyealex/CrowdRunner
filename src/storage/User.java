package storage;

import java.sql.Date;

/**
 * Created by Matthew on 5/10/2016.
 * Storage class for a user
 */
public class User {

    public String email;
    public String name;
    public Date birthDate, joinDate;
    public int selfChallenge;
    public int group1ID, group2ID, group3ID;

    public User(String email, String name, Date birthDate, Date joinDate, int selfChallenge,
                int group1ID, int group2ID, int group3ID) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.joinDate = joinDate;
        this.selfChallenge = selfChallenge;
        this.group1ID = group1ID;
        this.group2ID = group2ID;
        this.group3ID = group3ID;
    }

    @Override
    public String toString() {
        return String.format("User; email: %s; name: %s", email, name);
    }

}
