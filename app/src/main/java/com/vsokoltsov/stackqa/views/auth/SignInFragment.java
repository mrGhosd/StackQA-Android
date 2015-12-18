package com.vsokoltsov.stackqa.views.auth;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.messages.FailureRequestMessage;
import com.vsokoltsov.stackqa.messages.SuccessRequestMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 14.11.15.
 */
public class SignInFragment extends Fragment {
    private View fragmentView;
    private TextView errorAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.sign_in, container, false);
        errorAuth = (TextView) fragmentView.findViewById(R.id.authError);
        Button signInButton = (Button) fragmentView.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(v);
            }
        });
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void signIn(View v) {
        errorAuth.setVisibility(View.GONE);
        EditText email = (EditText) fragmentView.findViewById(R.id.emailField);
        EditText password = (EditText) fragmentView.findViewById(R.id.passwordField);

        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        if (!emailString.isEmpty() && !passwordString.isEmpty()) {
            try {
                AuthManager.getInstance().signIn(emailString, passwordString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            String emailError = "";
            String passwordError = "";
            if (emailString.isEmpty()) {
                emailError = "Email field is empty";
            }
            if(passwordString.isEmpty()) {
                passwordError = "Password field is empty";
            }
            String answer = emailError + "\n" + passwordError;
            errorAuth.setText(answer);
            errorAuth.setVisibility(View.VISIBLE);
        }
    }
}


