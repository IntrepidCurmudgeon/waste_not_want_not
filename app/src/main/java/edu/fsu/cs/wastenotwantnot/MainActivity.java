package edu.fsu.cs.wastenotwantnot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener {

    private UserRepository mUserRepo = new UserRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment fragment = new LoginFragment();
        String tag = LoginFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayoutFragment, fragment, tag).commit();
    }

    @Override
    public void onStartLogin(String userName, String password) {
        String correctPassword = mUserRepo.getPassword(userName);
        String toastText = "Passwords don't match";
        if (correctPassword.equals(password)) {
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
