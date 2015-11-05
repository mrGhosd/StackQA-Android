package com.vsokoltsov.stackqa.models;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 14.09.15.
 */
public class User {
    private String avatarUrl;
    private String surname;
    private String name;
    private String email;

    public User(JSONObject object){
        try {
            if(object.has("surname")) setSurname(object.getString("surname"));
            if(object.has("name")) setName(object.getString("name"));
            if(object.has("email")) setEmail(object.getString("email"));
            if (object.has("avatar")) setAvatarUrl(object.getJSONObject("avatar").getString("url"));
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void setAvatarUrl(String url){
        this.avatarUrl = url;
    }
    public String getAvatarUrl(){
        return this.avatarUrl;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }
    public String getSurname(){
        return this.surname;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public String getCorrectNaming(){
        if(!this.surname.isEmpty() && !this.name.isEmpty()){
            return this.surname + " " + this.name;
        } else {
            return this.email;
        }
    }
}
