package edu.fsu.cs.wastenotwantnot;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class WasteNotViewModel extends AndroidViewModel {

    private UserRepository mUserRepository;
    private ListingRepository mListingRepository;

    private final LiveData<List<Listing>> mListings;

    public WasteNotViewModel (Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mListingRepository = new ListingRepository(application);
        mListings = mListingRepository.getListings();
    }

    LiveData<List<Listing>> getListings() { return mListings; }

    public void insert(User user) { mUserRepository.insert(user); }

    public void insert(Listing listing) { mListingRepository.insert(listing); }

    public User loadUserByUserName (String userName, String pwd) {
        Log.d("WasteNotViewModel", "loadUserByUserName " + userName + " " + pwd);
        return mUserRepository.loadUserByUserName(userName, pwd);
    }

    public User loadUserByEmailOrUserName (String email, String userName) {
        return mUserRepository.loadUserByEmailOrUserName(email, userName);
    }
}
