package com.vsokoltsov.stackqa.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.util.ImageBitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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


}
