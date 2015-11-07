package com.vsokoltsov.stackqa.messages;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 06.11.15.
 */
public class QuestionMessage extends BaseMessage {
    public String operationName;
    public JSONObject response;
    public VolleyError error;

    public QuestionMessage(String name, JSONObject response){
        this.operationName = name;
        this.response = response;
    }

    public QuestionMessage(String name, VolleyError error){
        this.operationName = name;
        this.error = error;
    }

}
