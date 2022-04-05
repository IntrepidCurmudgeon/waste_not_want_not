package edu.fsu.cs.wastenotwantnot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        EditText firstName = view.findViewById(R.id.firstNameInput);
        EditText lastName = view.findViewById(R.id.lastNameInput);
        EditText emailAddress = view.findViewById(R.id.emailAddressInput);
        EditText address = view.findViewById(R.id.addressInput);
        EditText username = view.findViewById(R.id.usernameInput);
        EditText password = view.findViewById(R.id.passwordInput);
        EditText confirmPassword = view.findViewById(R.id.confirmPasswordInput);

        Button applyButton = view.findViewById(R.id.applyButton);
        Button resetButton = view.findViewById(R.id.resetButton);

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

            // Validate passwords are the same
            String passwordStr = password.getText().toString();
            String confirmPasswordStr = confirmPassword.getText().toString();
            if (!passwordStr.equals(confirmPasswordStr))
                Toast.makeText(getActivity(), "Passwords are not the same", Toast.LENGTH_SHORT).show();

            // TODO: validate user is not already in the database



            // TODO: insert user information to the database
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
}
