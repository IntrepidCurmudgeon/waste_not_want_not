package edu.fsu.cs.wastenotwantnot;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnRegisterFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        EditText firstName = view.findViewById(R.id.firstNameInput);
        EditText lastName = view.findViewById(R.id.lastNameInput);
        EditText emailAddress = view.findViewById(R.id.emailInput);
        EditText address = view.findViewById(R.id.addressInput);
        EditText username = view.findViewById(R.id.usernameInput);
        EditText password = view.findViewById(R.id.passwordInput);
        EditText confirmPassword = view.findViewById(R.id.confirmPasswordInput);

        Button applyButton = view.findViewById(R.id.applyBtn);
        Button resetButton = view.findViewById(R.id.resetBtn);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // applyButton on click listener
        applyButton.setOnClickListener(v -> {
            // Validate data has been entered
            if (firstName.getText().toString().equals(""))
                Toast.makeText(getActivity(), "First name is not entered", Toast.LENGTH_SHORT).show();
            if (lastName.getText().toString().equals(""))
                Toast.makeText(getActivity(), "Last name is not entered", Toast.LENGTH_SHORT).show();
            if (emailAddress.getText().toString().equals(""))
                Toast.makeText(getActivity(), "Email address is not entered", Toast.LENGTH_SHORT).show();
            if (address.getText().toString().equals(""))
                Toast.makeText(getActivity(), "Address is not entered", Toast.LENGTH_SHORT).show();
            if (username.getText().toString().equals(""))
                Toast.makeText(getActivity(), "Username is not entered", Toast.LENGTH_SHORT).show();
            if (password.getText().toString().equals(""))
                Toast.makeText(getActivity(), "Password is not entered", Toast.LENGTH_SHORT).show();
            if (confirmPassword.getText().toString().equals(""))
                Toast.makeText(getActivity(), "Confirm password", Toast.LENGTH_SHORT).show();
            if (!emailAddress.getText().toString().matches(emailPattern))
                Toast.makeText(getActivity(), "Email address is not a valid format", Toast.LENGTH_SHORT).show();

            // Validate passwords are the same
            String passwordStr = password.getText().toString();
            String confirmPasswordStr = confirmPassword.getText().toString();
            if (!passwordStr.equals(confirmPasswordStr))
                Toast.makeText(getActivity(), "Passwords are not the same", Toast.LENGTH_SHORT).show();

            else
            {
                User user = new User();
                user.setFirstName(firstName.getText().toString().trim());
                user.setLastName(lastName.getText().toString().trim());
                user.setEmailAddress(emailAddress.getText().toString().trim());
                user.setAddress(address.getText().toString().trim());
                user.setUserName(username.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                mListener.onRegistrationAttempt(user);
            }
        });

        // resetButton on click listener
        resetButton.setOnClickListener(v -> {
            // Clear EditTexts
            firstName.setText("");
            lastName.setText("");
            emailAddress.setText("");
            address.setText("");
            username.setText("");
            password.setText("");
            confirmPassword.setText("");
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnRegisterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegisterFragmentInteractionListener {
        void onRegistrationAttempt(User user);
    }
}
