package com.vsokoltsov.stackqa.messages;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 04.01.16.
 */
public class CategoryMessage extends BaseMessage {
    public String operationName;
    public JSONObject response;
    public VolleyError error;

    public CategoryMessage(String name, JSONObject response){
        this.operationName = name;
        this.response = response;
    }

    public CategoryMessage(String name, VolleyError error){
        this.operationName = name;
        this.error = error;
    }
}
