package edu.fsu.cs.wastenotwantnot;

import android.app.Application;
import android.util.Log;

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
    }

    // LiveData<List<Listing>> getAllListings() { return mAllListings; }
    public void insert(User user) { mRepository.insert(user); }

    //public void insert(Listing listing) { mRepository.insert(listing); } // TODO

    public User loadUserByUserName (String userName, String pwd) {
        Log.d("WasteNotViewModel", "loadUserByUserName " + userName + " " + pwd);
        return mRepository.loadUserByUserName(userName, pwd);
    }

    public User loadUserByEmailOrUserName (String email, String userName) {
        return mRepository.loadUserByEmailOrUserName(email, userName);
    }
}
