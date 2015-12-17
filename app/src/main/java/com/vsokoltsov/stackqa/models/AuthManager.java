package com.vsokoltsov.stackqa.models;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.UserMessage;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 16.11.15.
 */
public class AuthManager implements RequestCallbacks {
    private User currentUser;
    private String token;
    private JSONObject jsonUser;

    private static AuthManager ourInstance = new AuthManager();
    public static AuthManager getInstance() {
        return ourInstance;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setToken(String token) { this.token = token; }

    public String getToken() { return this.token; }

    public void signIn(String email, String password) throws JSONException {
        String url = AppController.APP_HOST+"/oauth/token";
        JSONObject params = new JSONObject();
        params.put("grant_type", "password");
        params.put("email", email);
        params.put("password", password);
        ApiRequest.getInstance(this).post(url, null, "sign_in", params);
    }

    public void signUp(String email, String password, String passwordConfirmation) throws JSONException {
        String url = AppController.APP_HOST+"/api/v1/users";
        jsonUser = new JSONObject();
        JSONObject user = new JSONObject();
        jsonUser .put("email", email);
        jsonUser .put("password", password);
        jsonUser .put("password_confirmation", passwordConfirmation);

        user.put("user", jsonUser );
        ApiRequest.getInstance(this).post(url, null, "sign_up", user);
    }

    public void currentUserRequest() throws JSONException {
        String url = AppController.APP_HOST + "/api/v1/profiles/me";
        ApiRequest.getInstance(this).get(url, null, "current_user", null);
    }

    public void signOut() {
        EventBus.getDefault().post(new UserMessage("signOut"));
    }


    @Override
    public void successCallback(String requestName, JSONObject object) throws JSONException {
        switch (requestName) {
            case "sign_in":
                setToken(object.getString("access_token"));
                currentUserRequest();
                break;
            case "current_user":
                parseCurrentUser(object);
                EventBus.getDefault().post(new UserMessage("currentUserSignedIn", this.getCurrentUser()));
                break;
            case "sign_up":
                parseSignedUpUser(object);
                break;
        }
    }

    @Override
    public void failureCallback(String requestName, VolleyError error) {

    }

    public void parseSignedUpUser(JSONObject object) throws JSONException {
        String email = jsonUser.getString("email");
        String password = jsonUser.getString("password");
        signIn(email, password);
    }

    private void parseCurrentUser(JSONObject response) {
        setCurrentUser(new User(response));
    }

}
