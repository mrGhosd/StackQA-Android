package com.vsokoltsov.stackqa.models;

import android.util.Log;
import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.controllers.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;

/**
 * Created by vsokoltsov on 03.07.15.
 */
public class ServerConnection {
    private static ServerConnection instance;

    private ServerConnection(){}

    public static synchronized ServerConnection getInstance(){
        if(instance == null){
            instance = new ServerConnection();
        }
        return instance;
    }
    private ServerCallbacks listCallbacks = serverCallbacks;

    public interface ServerCallbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void successCallback(JSONObject object, String requestID);
        public void errorCallback(VolleyError error, String requestID);
    }

    public void sendDataToUrlWithParametersAndCallbacks(String url, int requestType, final String requestID, JSONObject parameters){
        JsonObjectRequest request = new JsonObjectRequest(requestType, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listCallbacks.successCallback(response, requestID);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listCallbacks.errorCallback(error, requestID);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    private static ServerCallbacks serverCallbacks = new ServerCallbacks() {
        @Override
        public void successCallback(JSONObject object, String requestID){

        }
        @Override
        public void errorCallback(VolleyError error, String requestID){

        }
    };
}
