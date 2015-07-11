package com.vsokoltsov.stackqa.models;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by vsokoltsov on 11.07.15.
 */
public class Category {
    private String title;
    public Category(JSONObject category){
        try{
        setTitle(String.valueOf(category.getString("title")));
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
}
