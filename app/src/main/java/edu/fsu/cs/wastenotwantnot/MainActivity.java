package edu.fsu.cs.wastenotwantnot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.rxjava3.core.Single;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener,
        HomeFragment.OnHomeFragmentInteractionListener,
        RegisterFragment.OnRegisterFragmentInteractionListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private WasteNotViewModel mWasteNotViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWasteNotViewModel = new ViewModelProvider(this).get(WasteNotViewModel.class);

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
                    CreateListing fragment = new CreateListing();
                    String tag = CreateListing.class.getCanonicalName();
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.frameLayoutFragment, fragment, tag).commit();
                }
            }
        }).start();
    }

    @Override
    public void onStartRegister() {
        RegisterFragment registerFragment = new RegisterFragment();
        String registerFragmentTag = RegisterFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayoutFragment, registerFragment, registerFragmentTag).commit();
    }

    @Override
    public void onStartSignIn() {
        LoginFragment fragment = new LoginFragment();
        String tag = LoginFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayoutFragment, fragment, tag).commit();
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

                    // Go back to LoginFragment
                    LoginFragment fragment = new LoginFragment();
                    String tag = LoginFragment.class.getCanonicalName();
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.frameLayoutFragment, fragment, tag).commit();
                }
            }
        }).start();
    }
}
