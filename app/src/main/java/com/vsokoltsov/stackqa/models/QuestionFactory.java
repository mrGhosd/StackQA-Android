package com.vsokoltsov.stackqa.models;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.QuestionMessage;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 21.12.15.
 */
public class QuestionFactory implements RequestCallbacks {
    private static QuestionFactory ourInstance = new QuestionFactory();

    public static QuestionFactory getInstance() {
        return ourInstance;
    }

    private QuestionFactory() {
    }

    public void getCollection(){
        String url = AppController.APP_HOST + "/api/v1/questions";
        ApiRequest.getInstance(this).get(url, "list", null);
    }

    public void get(int id){
        String url = AppController.APP_HOST + "/api/v2/questions/" + id;
        ApiRequest.getInstance(this).get(url, "detail", null);
    }

    public void create(JSONObject params) {
        String url = AppController.APP_HOST + "/api/v1/questions/";
        ApiRequest.getInstance(this).post(url, "create", params);
    }

    @Override
    public void successCallback(String requestName, JSONObject object) {
        EventBus.getDefault().post(new QuestionMessage(requestName, object));
    }

    @Override
    public void failureCallback(String requestName, VolleyError error) {
        EventBus.getDefault().post(new QuestionMessage(requestName, error));
    }
}
