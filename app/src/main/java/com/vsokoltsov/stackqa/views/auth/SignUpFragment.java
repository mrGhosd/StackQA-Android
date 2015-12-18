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

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.messages.FailureRequestMessage;
import com.vsokoltsov.stackqa.messages.SuccessRequestMessage;
import com.vsokoltsov.stackqa.models.AuthManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 14.11.15.
 */
public class SignUpFragment extends Fragment{
    private View fragmentView;
    private TextView emailErrors;
    private TextView passwordErrors;
    private TextView passwordConfirmationErrors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.sign_up, container, false);
        emailErrors = (TextView) fragmentView.findViewById(R.id.emailErrors);
        passwordErrors = (TextView) fragmentView.findViewById(R.id.passwordErrors);
        passwordConfirmationErrors = (TextView) fragmentView.findViewById(R.id.passwordConfirmationErrors);

        Button signUpButton = (Button) fragmentView.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void signUp(View v) {
        emailErrors.setVisibility(View.GONE);
        passwordErrors.setVisibility(View.GONE);
        passwordConfirmationErrors.setVisibility(View.GONE);

        EditText email = (EditText) getView().findViewById(R.id.emailField);
        EditText password = (EditText) getView().findViewById(R.id.passwordField);
        EditText passwordConfirmation = (EditText) getView().findViewById(R.id.passwordConfirmationField);

        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        String passwordConfirmationString = passwordConfirmation.getText().toString();
        if(!emailString.isEmpty() && !passwordString.isEmpty() &&
                !passwordConfirmationString.isEmpty()) {
            try {
                AuthManager.getInstance().signUp(emailString, passwordString, passwordConfirmationString);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        else {
            if (emailString.isEmpty()) {
                emailErrors.setText("Email can't be empty");
                emailErrors.setVisibility(View.VISIBLE);
            }
            if (passwordString.isEmpty()) {
                passwordErrors.setText("Password can't be empty");
                passwordErrors.setVisibility(View.VISIBLE);
            }
            if (passwordConfirmationString.isEmpty()) {
                passwordConfirmationErrors.setText("Password confirmation can't be empty");
                passwordConfirmationErrors.setVisibility(View.VISIBLE);
            }
        }

    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // This method will be called when a MessageEvent is posted
    public void onEvent(SuccessRequestMessage event){
        switch (event.operationName){
            case "sign_up":

                break;
        }
    }

    public void onEvent(FailureRequestMessage event) throws JSONException {
        switch (event.operationName){
            case "sign_up":
                parseSignUpFailure(event.error);
                break;
        }
    }

    public void parseSignUpFailure(VolleyError error) throws JSONException {
        JSONObject json;

        String emailString = "";
        String passwordString = "";
        String passwordConfirmationString = "";

        NetworkResponse response = error.networkResponse;
        String serverResponse = new String(response.data);
        json = new JSONObject(serverResponse);

        if (json.has("email")) {
            JSONArray emailErrorsArray = json.getJSONArray("email");
            emailString = (String) emailErrorsArray.get(0);
            emailErrors.setText(emailString);
            emailErrors.setVisibility(View.VISIBLE);
        }
        if (json.has("password")) {
            JSONArray passwordErrorsArray = json.getJSONArray("password");
            passwordString = (String) passwordErrorsArray.get(0);
            passwordErrors.setText(passwordString);
            passwordErrors.setVisibility(View.VISIBLE);
        }
        if (json.has("password_confirmation")) {
            JSONArray passwordConfirmationErrorsArray = json.getJSONArray("password_confirmation");
            passwordConfirmationString = (String) passwordConfirmationErrorsArray.get(0);
            passwordConfirmationErrors.setText(passwordConfirmationString);
            passwordConfirmationErrors.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
