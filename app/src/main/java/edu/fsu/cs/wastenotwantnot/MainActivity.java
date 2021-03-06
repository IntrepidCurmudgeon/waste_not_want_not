package edu.fsu.cs.wastenotwantnot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener,
        HomeFragment.OnHomeFragmentInteractionListener,
        RegisterFragment.OnRegisterFragmentInteractionListener,
        CreateListing.OnCreateListingFragmentInteractionListener {

    public static WasteNotViewModel mWasteNotViewModel; //TODO: confirm that this should be public
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    public static double userLatitude;
    public static double userLongitude;
    public static String searchDistance;

    private boolean loggedIn = false; // Tracks if user is logged in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWasteNotViewModel = new ViewModelProvider(this).get(WasteNotViewModel.class);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        HomeFragment fragment = new HomeFragment();
        String tag = HomeFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayoutFragment, fragment, tag).commit();
    }

    @Override
    public void onStartLogin(String userName, String password) {
        Log.d("MainActivity", "userName: " + userName + " password: " + password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = mWasteNotViewModel.loadUserByUserName(userName, password);
                if (user == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    int userId = user.getId();
                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(getString(R.string.userId), userId);
                    editor.apply();
                    // Go to CreateListing
                    loggedIn = true;
                    CreateListing fragment = new CreateListing();
                    String tag = CreateListing.class.getCanonicalName();
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.frameLayoutFragment, fragment, tag).addToBackStack(null).commit();
                }
            }
        }).start();
    }

    @Override
    public void onStartRegister() {
        RegisterFragment registerFragment = new RegisterFragment();
        String registerFragmentTag = RegisterFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayoutFragment, registerFragment, registerFragmentTag).addToBackStack(null).commit();
    }

    @Override
    public void onStartSignIn() {
        if (loggedIn) {
            CreateListing createListing = new CreateListing();
            String listingTag = CreateListing.class.getCanonicalName();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.frameLayoutFragment, createListing, listingTag).commit();
        }
        else {
            LoginFragment fragment = new LoginFragment();
            String tag = LoginFragment.class.getCanonicalName();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.frameLayoutFragment, fragment, tag).commit();
        }
    }

    @Override
    public void onRegistrationAttempt(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User userAttempt = mWasteNotViewModel.loadUserByEmailOrUserName(user.getEmailAddress(), user.getUserName());
                if (userAttempt != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "User with that email or username already exists", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    // Insert user information to the database
                    mWasteNotViewModel.insert(user);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Success! Please log in.", Toast.LENGTH_LONG).show();
                        }
                    });

                    // Go back to LoginFragment
                    LoginFragment fragment = new LoginFragment();
                    String tag = LoginFragment.class.getCanonicalName();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayoutFragment, fragment, tag)
                            .commit();
                }
            }
        }).start();
    }

    @Override
    public void onStartListingSearch() {

        getLastLocation();

        ListingFragment fragment = new ListingFragment();
        String tag = ListingFragment.class.getCanonicalName();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutFragment, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            setUserLocation(location.getLatitude(),location.getLongitude());
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest(); //TODO: LocationRequest() is deprecated
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    @Override
    public void onListingCreationAttempt(Listing listing) {
        mWasteNotViewModel.insert(listing);
        Toast.makeText(getApplicationContext(), "Data successfully entered", Toast.LENGTH_SHORT).show();
    }

    public void setUserLocation(double latitude, double longitude){
        userLatitude = latitude;
        userLongitude = longitude;
    }
    public double getUsersLatitude(){
        return userLatitude;
    }
    public double getUsersLongitude(){
        return userLongitude;
    }

    //inflate options
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeMenu:
                HomeFragment homeFragment = new HomeFragment();
                String homeTag = HomeFragment.class.getCanonicalName();
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.frameLayoutFragment, homeFragment, homeTag).commit();
                break;
            case R.id.logOutMenu:
                if (loggedIn) {
                    loggedIn = false;
                    LoginFragment loginFragment = new LoginFragment();
                    String loginTag = LoginFragment.class.getCanonicalName();
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.frameLayoutFragment, loginFragment, loginTag).commit();
                }
                else
                    Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exitMenu:
                Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

    public WasteNotViewModel getmWasteNotViewModel(){
        return mWasteNotViewModel;
    }

    public String getSearchDistance(){
        if (isValid(searchDistance)){
            return searchDistance;
        }else{
            return "25";
        }
    }

    public void setSearchDistance(String sDistance){
        searchDistance = sDistance;
    }

    // method check if string is null or empty
    public static boolean isNullEmpty(String str) {

        // check if string is null
        if (str == null) {
            return true;
        }

        // check if string is empty
        else if(str.isEmpty()){
            return true;
        }

        else if(str.matches("-?\\d+")){
            return false;
        }

        else {
            return false;
        }
    }
    // method check if string is null or empty
    public static boolean isValid(String str) {

        if(str.matches("-?\\d+")){
            return true;
        }
        else {
            return false;
        }
    }
}
