package edu.fsu.cs.wastenotwantnot;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.concurrent.atomic.AtomicReference;

public class UserRepository {
    private UserDao mUserDao;
    //private ListingDao mListingDao; // TODO
    // TODO: livedata (for updating data as it changes)
    // e.g. private LiveData<List<Listing>> mAllListing;

    UserRepository(Application application) {
        Log.d("UserRepository", " is alive");
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        //mListingDao = db.listingDao(); // TODO
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
/*    LiveData<List<Listing>> getAllListings() {
        return mAllListings;
    }*/

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    /*
    // TODO
    void insert(Listing listing) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mListingDao.insert(listing);
        });
    }
    */

    public User loadUserByUserName(String userName, String password) {
        Log.d("UserRepository", userName + " " + password);
        return mUserDao.loadUserByUserName(userName, password);
    }

    public User loadUserByEmailOrUserName(String email, String userName) {
        return mUserDao.loadUserByEmailOrUserName(email, userName);
    }
}
