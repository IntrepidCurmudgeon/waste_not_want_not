package edu.fsu.cs.wastenotwantnot;

import android.app.Application;
import android.util.Log;

public class UserRepository {
    private UserDao mUserDao;

    UserRepository(Application application) {
        Log.d("UserRepository", " is alive");
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    public User loadUserByUserName(String userName, String password) {
        Log.d("UserRepository", userName + " " + password);
        return mUserDao.loadUserByUserName(userName, password);
    }

    public User loadUserByEmailOrUserName(String email, String userName) {
        return mUserDao.loadUserByEmailOrUserName(email, userName);
    }
}
