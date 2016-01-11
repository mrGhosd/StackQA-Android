package com.vsokoltsov.stackqa.messages;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 11.01.16.
 */
public class AnswerMessage extends BaseMessage {
    public String operationName;
    public JSONObject response;
    public VolleyError error;

    public AnswerMessage(String name, JSONObject response) {
        this.operationName = name;
        this.response = response;
    }

    public AnswerMessage(String name, VolleyError error) {
        this.operationName = name;
        this.error = error;
    }
}
