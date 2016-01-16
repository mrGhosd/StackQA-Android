package com.vsokoltsov.stackqa.views.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.vsokoltsov.stackqa.messages.UserMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.views.questions.list.QuestionsListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 14.11.15.
 */
public class SignUpFragment extends Fragment{
    private View fragmentView;
    private TextView emailErrors;
    private TextView passwordErrors;
    private TextView passwordConfirmationErrors;
    private ProgressDialog dialog;
    private Resources res;

    private EditText emailField;
    private EditText passwordField;
    private EditText passwordConfirmationField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.sign_up, container, false);
        res = getResources();
        emailErrors = (TextView) fragmentView.findViewById(R.id.emailErrors);
        passwordErrors = (TextView) fragmentView.findViewById(R.id.passwordErrors);
        passwordConfirmationErrors = (TextView) fragmentView.findViewById(R.id.passwordConfirmationErrors);

        emailField = (EditText) fragmentView.findViewById(R.id.emailField);
        passwordField = (EditText) fragmentView.findViewById(R.id.passwordField);
        passwordConfirmationField = (EditText) fragmentView.findViewById(R.id.passwordConfirmationField);

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
        emailField.setError(null);
        passwordField.setError(null);
        passwordConfirmationField.setError(null);

        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        String passwordConfirmationString = passwordConfirmationField.getText().toString();

        if(!emailString.isEmpty() && !passwordString.isEmpty() &&
                !passwordConfirmationString.isEmpty()) {
            try {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage(res.getString(R.string.sign_up_loader));
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                AuthManager.getInstance().signUp(emailString, passwordString, passwordConfirmationString);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        else {
            if (emailString.isEmpty()) {
                emailField.setError("Email can't be empty");
            }
            if (passwordString.isEmpty()) {
                passwordField.setError("Password can't be empty");
            }
            if (passwordConfirmationString.isEmpty()) {
                passwordConfirmationField.setError("Password confirmation can't be empty");
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

    public void onEvent(UserMessage event){
        dialog.hide();
        Intent detailIntent = new Intent(getActivity(), QuestionsListActivity.class);
        getActivity().startActivity(detailIntent);
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    // This method will be called when a MessageEvent is posted
    public void onEvent(SuccessRequestMessage event){
        switch (event.operationName){
            case "sign_up":
                dialog.hide();
                Intent detailIntent = new Intent(getActivity(), QuestionsListActivity.class);
                getActivity().startActivity(detailIntent);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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
            emailField.setError(emailString);
        }
        if (json.has("password")) {
            JSONArray passwordErrorsArray = json.getJSONArray("password");
            passwordString = (String) passwordErrorsArray.get(0);
            passwordField.setError(passwordString);
        }
        if (json.has("password_confirmation")) {
            JSONArray passwordConfirmationErrorsArray = json.getJSONArray("password_confirmation");
            passwordConfirmationString = (String) passwordConfirmationErrorsArray.get(0);
            passwordConfirmationField.setError(passwordConfirmationString);
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
