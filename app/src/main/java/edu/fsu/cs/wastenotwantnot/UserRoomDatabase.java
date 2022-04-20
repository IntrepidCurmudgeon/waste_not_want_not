package edu.fsu.cs.wastenotwantnot;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: set exportSchema to true, implement best practices
@Database(entities = {User.class, Listing.class}, version = 2, exportSchema = false) // TODO
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ListingDao listingDao(); // TODO

    private static volatile UserRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // TODO: confirm appropriate use of context
    static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "user_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDao dao = INSTANCE.userDao();

                //dao.deleteAll();

/*                User blake = new User();
                blake.setFirstName("Blake");
                blake.setLastName("Wilson");
                blake.setAddress("562 CR 1148");
                blake.setUserName("IpswichTriptych");
                blake.setPassword("COP4656");
                Log.d("UserRoomDatabase", blake.getUserName() + " " + blake.getPassword());
                dao.insert(blake);
                Log.d("UserRoomDatabase", "inserted user");*/
            });
        }
    };
}
