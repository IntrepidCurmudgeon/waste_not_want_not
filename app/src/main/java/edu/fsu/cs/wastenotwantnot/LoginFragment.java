package edu.fsu.cs.wastenotwantnot;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_login, parent, false);
        Button loginButton = view.findViewById(R.id.btnLogin);
        EditText userNameText = view.findViewById(R.id.txtUsername);
        EditText passwordText = view.findViewById(R.id.txtPassword);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString().trim();
                String pwd = passwordText.getText().toString().trim();
                Log.d("LOGIN FRAGMENT", "userName: " + userName + " pwd: " + pwd);
                mListener.onStartLogin(userName, pwd);
            }
        });
        Button registerButton = view.findViewById(R.id.btnSignup);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStartRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onStartLogin(String userName, String password);
        void onStartRegister();
    }
}
