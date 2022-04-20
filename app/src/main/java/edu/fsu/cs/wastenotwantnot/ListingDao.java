package edu.fsu.cs.wastenotwantnot;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ListingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Listing listing);

    @Query("DELETE FROM listing")
    void deleteAll();

    @Query("SELECT * FROM listing")
    LiveData<List<Listing>> getListings();
}

