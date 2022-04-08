package edu.fsu.cs.wastenotwantnot;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    // TODO: confirm conflict strategy
    // TODO: uncomment User.class
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    // TODO: Query
}
