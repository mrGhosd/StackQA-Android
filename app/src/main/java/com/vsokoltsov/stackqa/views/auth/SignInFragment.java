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
import com.vsokoltsov.stackqa.messages.UserMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.views.questions.list.QuestionsListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 14.11.15.
 */
public class SignInFragment extends Fragment {
    private View fragmentView;
    private TextView errorAuth;
    private EditText emailField;
    private EditText passwordField;
    private ProgressDialog dialog;
    private Resources res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.sign_in, container, false);
        res = getResources();
        errorAuth = (TextView) fragmentView.findViewById(R.id.authError);
        emailField = (EditText) fragmentView.findViewById(R.id.emailField);
        passwordField= (EditText) fragmentView.findViewById(R.id.passwordField);
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
        emailField.setError(null);
        passwordField.setError(null);

        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        if (!emailString.isEmpty() && !passwordString.isEmpty()) {
            try {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage(res.getString(R.string.sign_in_loader));
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                AuthManager.getInstance().signIn(emailString, passwordString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            String emailError = "";
            String passwordError = "";
            if (emailString.isEmpty()) {
                emailField.setError(res.getString(R.string.email_field_error));
            }
            if(passwordString.isEmpty()) {
                passwordField.setError(res.getString(R.string.password_field_error));
            }
        }
    }

    @Override
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

    public void onEvent(FailureRequestMessage event) throws JSONException {
        switch (event.operationName){
            case "sign_in":
                parseSignUpFailure(event.error);
                break;
        }
    }

    public void parseSignUpFailure(VolleyError error) throws JSONException {
        JSONObject json;

        String emailString = "";

        NetworkResponse response = error.networkResponse;
        String serverResponse = new String(response.data);
        json = new JSONObject(serverResponse);

        if (json.has("error_description")) {
            emailString = "There is no such user. Check your email or password.";
            emailField.setError(emailString);
        }
        dialog.cancel();
    }
}


