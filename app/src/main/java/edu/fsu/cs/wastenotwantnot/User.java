package edu.fsu.cs.wastenotwantnot;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user",
        indices = {@Index(value = {"user_name"},unique = true),
                @Index(value = {"email"},unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    public void setId (int new_id) {
        this.id = new_id;
    }

    public int getId () {
        return this.id;
    }

    @NonNull
    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "email")
    private String emailAddress;

    @ColumnInfo(name = "address")
    private String address;

    // TODO: password (encryption, best practices, etc)
    // can't just have password in plaintext in local database with a public setter/getter
    //
    @ColumnInfo(name = "password")
    private String password;

    public String getUserName () {
        return this.userName;
    }

    public String getFirstName () {
        return this.firstName;
    }

    public String getLastName () {
        return this.lastName;
    }

    public String getEmailAddress () {
        return this.emailAddress;
    }

    public String getAddress () {
        return this.address;
    }

    // TODO: don't do this!
    public String getPassword () {
        return this.password;
    }

    public void setUserName (String name) {
        this.userName = name;
    }

    public void setFirstName (String name) {
        this.firstName = name;
    }

    public void setLastName (String name) {
        this.lastName = name;
    }

    public void setEmailAddress (String email) {
        this.emailAddress = email;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    // TODO: don't do this!
    public void setPassword (String password) {
        this.password = password;
    }
}
