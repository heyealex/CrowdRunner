package storage;

import java.sql.Date;

/**
 * Storage class for a user
 *
 * Author: Matthew Erickson
 */
public class User {

    public String email;
    public String name;
    public Date birthDate, joinDate;
    public int selfChallenge;
    public int crowd1ID, crowd2ID, crowd3ID;

    public User(String email, String name, Date birthDate, Date joinDate, int selfChallenge,
                int crowd1ID, int crowd2ID, int crowd3ID) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.joinDate = joinDate;
        this.selfChallenge = selfChallenge;
        this.crowd1ID = crowd1ID;
        this.crowd2ID = crowd2ID;
        this.crowd3ID = crowd3ID;
    }

    @Override
    public String toString() {
        return String.format("User; email: %s; name: %s", email, name);
    }

}
