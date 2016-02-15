package com.vsokoltsov.stackqa.messages;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 16.02.16.
 */
public class CommentMessage extends BaseMessage {
    public String operationName;
    public JSONObject response;
    public VolleyError error;

    public CommentMessage(String name, JSONObject response) {
        this.operationName = name;
        this.response = response;
    }

    public CommentMessage(String name, VolleyError error) {
        this.operationName = name;
        this.error = error;
    }
}
