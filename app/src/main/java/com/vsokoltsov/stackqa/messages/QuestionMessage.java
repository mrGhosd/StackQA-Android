package com.vsokoltsov.stackqa.messages;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 06.11.15.
 */
public class QuestionMessage extends BaseMessage {
    public String operationName;
    public JSONObject response;

    public QuestionMessage(String name, JSONObject response){
        this.operationName = name;
        this.response = response;
    }

}
