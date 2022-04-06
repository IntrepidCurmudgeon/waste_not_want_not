package edu.fsu.cs.wastenotwantnot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment fragment = new LoginFragment();
        String tag = LoginFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLogin, fragment, tag).commit();
    }

    @Override
    public void onStartRegister() {
        RegisterFragment registerFragment = new RegisterFragment();
        String registerFragmentTag = RegisterFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragmentContainerView, registerFragment, registerFragmentTag).commit();
    }
}
