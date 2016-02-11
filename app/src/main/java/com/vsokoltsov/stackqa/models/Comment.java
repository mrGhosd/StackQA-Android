package com.vsokoltsov.stackqa.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vsokoltsov on 07.01.16.
 */
public class Comment {
    private int id;
    private String text;
    private User user;
    private Date createdAt;

    public Comment(JSONObject object){
        try {
            if (object.has("id")) setId(object.getInt("id"));
            if (object.has("text")) setText(object.getString("text"));
            if (object.has("user")) setUser(object.getJSONObject("user"));
            if (object.has("created_at")) setCreatedAt(object.getString("created_at"));
        } catch(JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
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

    public User getUser() {
        return user;
    }

    public void setUser(JSONObject user) {
        this.user = new User(user);
    }

    public String getCreatedAt(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        String str = sdf.format(createdAt); // formats to 09/23/2009 13:53:28.23
        return str;
    }

    public void setCreatedAt(String createdAt) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        Date date = format.parse(createdAt);
        this.createdAt = date;
    }
}
