package com.vsokoltsov.stackqa.models;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.QuestionMessage;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONException;
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

    public void getCollection(int page){
        String url = AppController.APP_HOST + "/api/v1/questions";
        JSONObject listParams = new JSONObject();
        try {
            listParams.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiRequest.getInstance(this).get(url, "list", listParams);
    }

    public void get(int id){
        String url = AppController.APP_HOST + "/api/v2/questions/" + id;
        ApiRequest.getInstance(this).get(url, "detail", null);
    }

    public void create(JSONObject params) {
        String url = AppController.APP_HOST + "/api/v1/questions/";
        ApiRequest.getInstance(this).post(url, "create", params);
    }

    public void update(int id, JSONObject params) {
        String url = AppController.APP_HOST + "/api/v1/questions/" + id;
        ApiRequest.getInstance(this).put(url, "update", params);
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
