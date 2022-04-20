package edu.fsu.cs.wastenotwantnot;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ListingFragment extends Fragment {
    private LiveData<List<Listing>> mListings;
    double userLatitude;
    double userLongitude;
    String searchDistance;
    private WasteNotViewModel mWasteNotViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listing, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ListingListAdapter adapter = new ListingListAdapter(new ListingListAdapter.ListingDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mWasteNotViewModel = new ViewModelProvider(this).get(WasteNotViewModel.class);
        mWasteNotViewModel.getListings().observe(getViewLifecycleOwner(), listings -> {
            // update the cached copy of the listings in the adapter
            adapter.submitList(listings);
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        // TODO: filter listings results by selected distance
        MainActivity mActivity = (MainActivity) getActivity();
        mWasteNotViewModel = mActivity.getmWasteNotViewModel();



        searchDistance = mActivity.getSearchDistance();

        userLatitude = mActivity.getUsersLatitude();
        userLongitude = mActivity.getUsersLongitude();
        LatLng usersLatLng = new LatLng(userLatitude,userLongitude);
        LiveData<List<Listing>> listingList = mWasteNotViewModel.getListings();

        // TODO: loop thru list and determine if posting is within selected distance, if so add it to recycler view
        String testAddress = "1600 Pennsylvania Ave NW, Washington, DC 20500";
        LatLng listingLatLng = getLocationFromAddress(getContext(),testAddress);


        double distance = getDistanceBetweenPoints(usersLatLng,listingLatLng);

        // TODO: If distance is within range (searchDistance) show it, NOTE distance is returned in meters not miles
        //int test = Integer.parseInt(searchDistance);*/
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public double getDistanceBetweenPoints(LatLng userLatLng, LatLng listingLatLng){
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(userLatLng.latitude);
        startPoint.setLongitude(userLatLng.longitude);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(listingLatLng.latitude);
        endPoint.setLongitude(listingLatLng.longitude);

        double distance=startPoint.distanceTo(endPoint);

        return distance;
    }
}