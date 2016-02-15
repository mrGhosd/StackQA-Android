package com.vsokoltsov.stackqa.models;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.CommentMessage;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 16.02.16.
 */
public class CommentFactory implements RequestCallbacks {
    private static CommentFactory ourInstance = new CommentFactory();

    public static CommentFactory getInstance() {
        return ourInstance;
    }

    public CommentFactory() {}

    public void createForQuestion(int questionId, JSONObject params) {
        String url = AppController.APP_HOST + "/api/v1/questions/" + questionId + "/comments";
        ApiRequest.getInstance(this).post(url, "create", params);
    }

    @Override
    public void successCallback(String requestName, JSONObject object) throws JSONException {
        EventBus.getDefault().post(new CommentMessage(requestName, object));
    }

    @Override
    public void failureCallback(String requestName, VolleyError error) {
        EventBus.getDefault().post(new CommentMessage(requestName, error));
    }
}
