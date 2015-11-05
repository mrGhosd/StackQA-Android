package com.vsokoltsov.stackqa.messages;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by vsokoltsov on 06.11.15.
 */
public class FailureRequestMessage extends BaseMessage {
    public VolleyError error;


}
