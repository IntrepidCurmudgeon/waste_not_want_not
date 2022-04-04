package edu.fsu.cs.wastenotwantnot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {



    public static LoginFragment getInstance(String username){
        //Bundle bundle = new Bundle();
        //bundle.putInt("USERNAME", username);
        LoginFragment fragment = new LoginFragment();
        //fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_login, parent, false);
        //login = view.findViewById(R.id.login);
        //username = view.findViewById(R.id.user);
        // password = view.findViewById(R.id.password);
        //String name = getArguments().getInt("USERNAME");
        //username.setText(username);
        return view;
    }

}
