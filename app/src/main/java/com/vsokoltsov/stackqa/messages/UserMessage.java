package com.vsokoltsov.stackqa.messages;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.models.User;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 29.11.15.
 */
public class UserMessage extends BaseMessage {
    public String operationName;
    public JSONObject response;
    public VolleyError error;

    public UserMessage(String name, User user){
        this.operationName = name;
        this.response = response;
    }

    public UserMessage(String name) {
        this.operationName = name;
        this.response = null;
    }

    public UserMessage(String name, VolleyError error){
        this.operationName = name;
        this.error = error;
    }
}
