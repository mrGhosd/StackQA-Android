package com.vsokoltsov.stackqa.messages;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 06.11.15.
 */
public class SuccessRequestMessage extends BaseMessage {
    public JSONObject response;
    public String requestName;

    public SuccessRequestMessage(String name, JSONObject object){
        this.requestName = name;
        this.response = object;
    }
}
