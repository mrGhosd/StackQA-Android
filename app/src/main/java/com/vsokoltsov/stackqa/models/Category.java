package com.vsokoltsov.stackqa.models;
import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.CategoryMessage;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 11.07.15.
 */
public class Category implements RequestCallbacks {
    private String title;
    private String description;
    private String imageUrl;
    private int id;

    public Category() {}

    public Category(JSONObject category){
        try{
            String id = category.getString("id");
            int intId = Integer.valueOf(id);
            if (category.has("id")) setId(intId);
            if (category.has("title")) setTitle(String.valueOf(category.getString("title")));
            if (category.has("description")) setDescription(String.valueOf(category.getString("description")));
            if (category.has("image")) setImageUrl(String.valueOf(category.getJSONObject("image").getString("url")));
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setImageUrl(String url){
        this.imageUrl = url;
    }
    public String getImageUrl(){
        return this.imageUrl;
    }

    public void getCollection() {
        String url = AppController.APP_HOST + "/api/v1/categories";
        ApiRequest.getInstance(this).get(url, "categories_collection", null);
    }

    @Override
    public void successCallback(String requestName, JSONObject object) throws JSONException {
        switch (requestName){
            case "categories_collection":
                EventBus.getDefault().post(new CategoryMessage(requestName, object));
                break;
        }
    }

    @Override
    public void failureCallback(String requestName, VolleyError error) {
        switch (requestName){
            case "categories_collection":
                EventBus.getDefault().post(new CategoryMessage(requestName, error));
                break;
        }
    }


}
