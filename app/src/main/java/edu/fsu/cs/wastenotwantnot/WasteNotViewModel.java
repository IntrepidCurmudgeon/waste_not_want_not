package edu.fsu.cs.wastenotwantnot;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.reactivex.rxjava3.core.Single;

public class WasteNotViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    // TODO: all listings live data
    //private final LiveData<List<Listing>> mAllListings;

    public WasteNotViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        //mAllListings = mRepository.getAllListings();
    }

    // LiveData<List<Listing>> getAllListings() { return mAllListings; }
    public void insert(User user) { mRepository.insert(user); }

    public User loadUserByUserName (String userName, String pwd) { return mRepository.loadUserByUserName(userName, pwd); }
}
