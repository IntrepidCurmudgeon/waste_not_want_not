package edu.fsu.cs.wastenotwantnot;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "listing",
        foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "user_id",
        onDelete = ForeignKey.CASCADE))
public class Listing {

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
    @ColumnInfo(name = "user_id", index = true)
    private int userId;

    @NonNull
    @ColumnInfo(name = "listing_description")
    private String listingDescription;

    @ColumnInfo(name = "listing_address")
    private String listingAddress;

    @ColumnInfo(name = "listing_title")
    private String listingTitle;

    // TODO: password (encryption, best practices, etc)
    // can't just have password in plaintext in local database with a public setter/getter
    //

    // TODO: getters
    public String getListingDescription () {
        return this.listingDescription;
    }

    public String getListingAddress () {
        return this.listingAddress;
    }

    public String getListingTitle () {
        return this.listingTitle;
    }

    public int getUserId () {
        return this.userId;
    }

    // TODO: setters

    public void setListingDescription (String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public void setListingAddress (String listingAddress) {
        this.listingAddress = listingAddress;
    }

    public void setListingTitle (String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public void setUserId (int id) {
        this.userId = id;
    }
}


//for listing database table
