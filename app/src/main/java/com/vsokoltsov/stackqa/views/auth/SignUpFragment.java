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

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.AuthManager;

import org.json.JSONException;

/**
 * Created by vsokoltsov on 14.11.15.
 */
public class SignUpFragment extends Fragment{
    private View fragmentView;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editor = (SharedPreferences.Editor) getActivity().getSharedPreferences("stackqa", Context.MODE_PRIVATE).edit();
        fragmentView =  inflater.inflate(R.layout.sign_up, container, false);
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
        EditText email = (EditText) getView().findViewById(R.id.emailField);
        EditText password = (EditText) getView().findViewById(R.id.passwordField);
        EditText passwordConfirmation = (EditText) getView().findViewById(R.id.passwordConfirmationField);

        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        String passwordConfirmationString = passwordConfirmation.getText().toString();
        editor.putString("stackqaemail", emailString);
        editor.putString("stackqapassword", passwordString);
        editor.commit();
        try {
            AuthManager.getInstance().signUp(emailString, passwordString, passwordConfirmationString);
        } catch (JSONException e){
            e.printStackTrace();
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
