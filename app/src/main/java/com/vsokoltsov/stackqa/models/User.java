package com.vsokoltsov.stackqa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.toolbox.ImageLoader;
import com.vsokoltsov.stackqa.controllers.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by vsokoltsov on 14.09.15.
 */
public class User implements Parcelable{
    private String avatarUrl;
    private String surname;
    private String name;
    private String email;
    private int id;

    public User(JSONObject object){
        try {
            if(object.has("id")) setId(Integer.valueOf(object.getString("id")));
            if(object.has("surname")) setSurname(object.getString("surname"));
            if(object.has("name")) setName(object.getString("name"));
            if(object.has("email")) setEmail(object.getString("email"));
            if (object.has("avatar")) setAvatarUrl(object.getJSONObject("avatar").getString("url"));
        } catch(JSONException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User() {

    }

    public void setAvatarUrl(String url) throws MalformedURLException, IOException {
        this.avatarUrl = url;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
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
        boolean surnameIsEmpty = this.surname.isEmpty() || this.surname == "null";
        boolean nameIsEmpty = this.name.isEmpty() || this.name == "null";

        if(!surnameIsEmpty && !nameIsEmpty){
            return this.surname + " " + this.name;
        } else {
            return this.email;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            User answer = new User();
            answer.surname = in.readString();
            answer.id = in.readInt();
            answer.name = in.readString();
            answer.email = in.readString();
            answer.avatarUrl = in.readString();
            return answer;
        }
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(surname);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(avatarUrl);
    }
}
