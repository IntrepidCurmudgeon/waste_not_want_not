package edu.fsu.cs.wastenotwantnot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
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

        LoginFragment fragment = new LoginFragment();
        String tag = LoginFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayoutFragment, fragment, tag).commit();

        mWasteNotViewModel = new ViewModelProvider(this).get(WasteNotViewModel.class);
    }

    @Override
    public void onStartLogin(String userName, String password) {
        User user = mWasteNotViewModel.loadUserByUserName(userName);
        String toastText = "Passwords don't match";
        if (user.getPassword().equals(password)) {
            toastText = "Passwords match!";
        }
        Toast.makeText(getApplicationContext(),
                toastText,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartRegister() {
        RegisterFragment registerFragment = new RegisterFragment();
        String registerFragmentTag = RegisterFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayoutFragment, registerFragment, registerFragmentTag).commit();
    }
}
