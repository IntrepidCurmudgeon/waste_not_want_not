package edu.fsu.cs.wastenotwantnot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
//test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginFragment fragment = new LoginFragment();
        String tag = LoginFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLogin, fragment, tag).commit();
    }
}

//doug was here
