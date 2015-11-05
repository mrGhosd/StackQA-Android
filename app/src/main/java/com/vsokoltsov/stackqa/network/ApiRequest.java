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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    private final String PACKAGE_PATH = "com.vsokoltsov.stackqa.";

    public static ApiRequest getInstance(RequestCallbacks callbacks) {
        ApiRequest.callbacks = callbacks;
        return ourInstance;
    }

    private ApiRequest() {
    }

    public void get(String url, String requestName, String operationID, JSONObject parameters){
        sendRequest(Request.Method.GET, url, requestName, operationID, parameters);
    }

    private void sendRequest(int requestType, String url, final String requestName, final String operationID, JSONObject parameters) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(requestType, url, parameters,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        EventBus.getDefault().post(instanceOfClass(requestName, operationID, response));
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

    private Object instanceOfClass(String className, String operationType, JSONObject response)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName(fullPackageName(className));
        Constructor<?> ctor = clazz.getConstructor(String.class, JSONObject.class);
        return ctor.newInstance(new Object[] { operationType, response });
    }

    private String fullPackageName(String name){
        return PACKAGE_PATH + name;
    }
}
