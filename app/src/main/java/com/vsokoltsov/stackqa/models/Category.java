package com.vsokoltsov.stackqa.models;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by vsokoltsov on 11.07.15.
 */
public class Category {
    private String title;
    private String description;
    private String imageUrl;

    public Category(JSONObject category){
        try{
            if (category.has("title")) setTitle(String.valueOf(category.getString("title")));
            if (category.has("description")) setDescription(String.valueOf(category.getString("description")));
            if (category.has("image")) setImageUrl(String.valueOf(category.getJSONObject("image").getString("url")));
        } catch(JSONException e){
            e.printStackTrace();
        }
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
}
