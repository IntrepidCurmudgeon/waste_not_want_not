package edu.fsu.cs.wastenotwantnot;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {
    // TODO: confirm conflict strategy
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    // TODO: Query
    // e.g. lookup user for login

    @Query("SELECT * FROM user WHERE user_name = :userName")
    public User loadUserByUserName(String userName);
}
