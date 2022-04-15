package edu.fsu.cs.wastenotwantnot;

import android.app.Application;

public class UserRepository {
    private UserDao mUserDao;
    // TODO: livedata (for updating data as it changes)
    // e.g. private LiveData<List<Word>> mAllWords;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
//        mAllWords = mWordDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
/*    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }*/

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    String getPassword(String userName) {
        return mUserDao.getPassword(userName);
    }
}
