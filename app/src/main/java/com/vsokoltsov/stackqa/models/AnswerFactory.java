package com.vsokoltsov.stackqa.models;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.AnswerMessage;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 11.01.16.
 */
public class AnswerFactory implements RequestCallbacks {
    private static AnswerFactory ourInstance = new AnswerFactory();

    public static AnswerFactory getInstance() {
        return ourInstance;
    }

    public AnswerFactory() {}

    public void create(int questionId, JSONObject params) {
        String url = AppController.APP_HOST + "/api/v1/questions/" + questionId + "/answers";
        ApiRequest.getInstance(this).post(url, "create", params);
    }

    public void update(int questionId, int answerId, JSONObject params) {
        String url = AppController.APP_HOST + "/api/v1/questions/" + questionId + "/answers/" + answerId;
        ApiRequest.getInstance(this).put(url, "update", params);
    }

    public void markAsHelpfull(int questionId, int answerId) {
        String url = AppController.APP_HOST + "/api/v1/questions/" + questionId + "/answers/" + answerId + "/helpfull";
        ApiRequest.getInstance(this).post(url, "helpfull", null);
    }

    @Override
    public void successCallback(String requestName, JSONObject object) throws JSONException {
        EventBus.getDefault().post(new AnswerMessage(requestName, object));
    }

    @Override
    public void failureCallback(String requestName, VolleyError error) {
        EventBus.getDefault().post(new AnswerMessage(requestName, error));
    }
}
