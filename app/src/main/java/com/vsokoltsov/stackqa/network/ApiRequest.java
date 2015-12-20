package com.vsokoltsov.stackqa.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.BaseMessage;
import com.vsokoltsov.stackqa.messages.FailureRequestMessage;
import com.vsokoltsov.stackqa.messages.SuccessRequestMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.models.QuestionFactory;

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

    public void post(String url, String requestName, String operationID, JSONObject parameters){
        sendRequest(Request.Method.POST, url, requestName, operationID, parameters);
    }

    private void sendRequest(int requestType, String url, final String requestName, final String operationID, JSONObject parameters) {
        AuthManager manager = AuthManager.getInstance();
        if (manager.getToken() != null) {
            url += "?access_token=" + manager.getToken();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(requestType, url, parameters,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
//                        EventBus.getDefault().post(new SuccessRequestMessage(operationID, response));
                        callbacks.successCallback(operationID, response);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callbacks.failureCallback(operationID, error);
//                    EventBus.getDefault().post(new FailureRequestMessage(operationID, error));
                }
            });
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
