package com.vsokoltsov.stackqa.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.SuccessRequestMessage;
import com.vsokoltsov.stackqa.models.Question;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 05.11.15.
 */
public class ApiRequest {
    private static ApiRequest ourInstance = new ApiRequest();
    private static RequestCallbacks callbacks;
    public static ApiRequest getInstance() {
        return ourInstance;
    }

    public static ApiRequest getInstance(RequestCallbacks callbacks) {
        ApiRequest.callbacks = callbacks;
        return ourInstance;
    }

    private ApiRequest() {
    }

    public void get(String url, String requestName, JSONObject parameters){
        sendRequest(Request.Method.GET, url, requestName, parameters);
    }

    private void sendRequest(int requestType, String url, final String requestName, JSONObject parameters) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(requestType, url, parameters,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        EventBus.getDefault().post(new SuccessRequestMessage(requestName, response));
//                        successCallback(requestName, response);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callbacks.failureCallback(requestName, error);
                }
            });
        AppController.getInstance().addToRequestQueue(objectRequest);
    }
}
