package edu.fsu.cs.wastenotwantnot;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ListingRepository {
    private ListingDao mListingDao;
    private LiveData<List<Listing>> mListings;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    ListingRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mListingDao = db.listingDao();
        mListings = mListingDao.getListings();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Listing>> getListings() {
        return mListings;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Listing listing) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mListingDao.insert(listing);
        });
    }
}
