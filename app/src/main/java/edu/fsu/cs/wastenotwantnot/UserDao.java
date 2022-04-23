package edu.fsu.cs.wastenotwantnot;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;



@Dao
public interface UserDao {
    // TODO: confirm conflict strategy
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user WHERE user_name=(:userName) AND password=(:pwd)")
    User loadUserByUserName(String userName, String pwd);

    @Query("SELECT * FROM user WHERE email=(:email) OR user_name=(:userName)")
    User loadUserByEmailOrUserName(String email, String userName);
}
