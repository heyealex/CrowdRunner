package storage;

import java.sql.Date;

/**
 * Storage class for a crowd (AKA Group but that's a reserved word in SQL)
 *
 * Author: Matthew Erickson
 */
public class Crowd {

    public int id;
    public String name;
    public Date joinDate;
    public String adminEmail;

    public Crowd(int id, String name, Date joinDate, String adminEmail) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.adminEmail = adminEmail;
    }

    @Override
    public String toString() {
        return String.format("Crowd; name: %s; admin: %s;", name, adminEmail);
    }

}
