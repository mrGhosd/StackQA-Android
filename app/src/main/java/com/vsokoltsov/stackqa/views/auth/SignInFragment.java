package com.vsokoltsov.stackqa.views.auth;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.sign_in, container, false);
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
        EditText email = (EditText) getView().findViewById(R.id.emailField);
        EditText password = (EditText) getView().findViewById(R.id.passwordField);

        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        try {
            AuthManager.getInstance().signIn(emailString, passwordString);
        } catch (JSONException e){
            e.printStackTrace();
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
            case "sign_in":
                this.parseSuccessCallbacks(event.response);
                break;
            case "current_user":
                this.parseCurrentUserRequest(event.response);
                break;
        }
    }

    public void onEvent(FailureRequestMessage event){
        switch (event.operationName){
            case "sign_in":
                break;
        }
    }

    public void parseSuccessCallbacks(JSONObject response){
        try {
            String token = (String) response.get("access_token");
            AuthManager.getInstance().setToken(token);
            AuthManager.getInstance().currentUserRequest();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void parseCurrentUserRequest(JSONObject response){
        User user = new User(response);
    }
}


