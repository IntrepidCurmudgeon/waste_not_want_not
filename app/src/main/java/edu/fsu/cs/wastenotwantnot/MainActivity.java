package edu.fsu.cs.wastenotwantnot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.rxjava3.core.Single;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private WasteNotViewModel mWasteNotViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWasteNotViewModel = new ViewModelProvider(this).get(WasteNotViewModel.class);

        LoginFragment fragment = new LoginFragment();
        String tag = LoginFragment.class.getCanonicalName();
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
                    //Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    HomeFragment homeFragment = new HomeFragment();
                    String homeFragmentTag = HomeFragment.class.getCanonicalName();
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.frameLayoutFragment, homeFragment, homeFragmentTag).commit();
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
}
