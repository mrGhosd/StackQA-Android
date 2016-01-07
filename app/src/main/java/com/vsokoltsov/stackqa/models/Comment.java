package com.vsokoltsov.stackqa.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vsokoltsov on 07.01.16.
 */
public class Comment {
    private int id;
    private String text;

    public Comment(JSONObject object){
        try {
            if (object.has("id")) setId(object.getInt("id"));
            if (object.has("text")) setText(object.getString("text"));
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
