package com.vsokoltsov.stackqa.messages;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 06.11.15.
 */
public class SuccessRequestMessage extends BaseMessage {
    public String operationName;
    public JSONObject response;

    public SuccessRequestMessage(String name, JSONObject response){
        this.operationName = name;
        this.response = response;
    }
}
