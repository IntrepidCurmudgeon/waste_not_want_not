package edu.fsu.cs.wastenotwantnot;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public static LoginFragment getInstance(String username){
        //Bundle bundle = new Bundle();
        // TODO: username - I think this needs to be putString (not putInt)
        //bundle.putInt("USERNAME", username);
        // TODO: password
        LoginFragment fragment = new LoginFragment();
        //fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_login, parent, false);
        Button loginButton = view.findViewById(R.id.btnLogin);
        EditText userNameText = view.findViewById(R.id.txtUsername);
        EditText passwordText = view.findViewById(R.id.txtPassword);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { mListener.onStartLogin(userNameText.getText().toString(), passwordText.getText().toString()); }
        });
        // TODO: type of name needs to match "USERNAME" key in bundle.putString()
        //String name = getArguments().getInt("USERNAME");
        //username.setText(username);
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
            throw new RuntimeException(context.toString()
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
