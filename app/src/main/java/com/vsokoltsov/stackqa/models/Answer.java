package com.vsokoltsov.stackqa.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vsokoltsov on 19.09.15.
 */
public class Answer implements Parcelable {
    private int id;
    private int questionID;
    private String text;
    private User user;
    private Date createdAt;
    private int commentsCount;

    public Answer(){}

    public Answer(JSONObject object){
        try {
            if (object.has("id")) setID(object.getInt("id"));
            if (object.has("text")) setText(object.getString("text"));
            if (object.has("user")) setUser(object.getJSONObject("user"));
            if (object.has("question_id")) setQuestionID(object.getInt("question_id"));
            if (object.has("created_at")) setCreatedAt(object.getString("created_at"));
            if (object.has("comments_count")) setCommentsCount(object.getInt("comments_count"));
        } catch(JSONException e){
            e.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        }
    }

    public int getID(){ return id; }

    public void setID(int id){ this.id = id; }

    public int getQuestionID(){ return questionID; }

    public void setQuestionID(int questionID){ this.questionID = questionID; }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public User getUser(){
        return this.user;
    }

    public void setCommentsCount(int count){
        this.commentsCount = count;
    }

    public int getCommentsCount(){
        return this.commentsCount;
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

    public void setUser(JSONObject user){
      this.user = new User(user);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        public Answer createFromParcel(Parcel in) {
            Answer answer = new Answer();
            answer.text = in.readString();
            answer.id = in.readInt();
            answer.questionID = in.readInt();
            try {
                answer.setCreatedAt(in.readString());
            } catch (ParseException e){
                e.printStackTrace();
            }
            return answer;
        }
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(id);
        dest.writeInt(questionID);
        dest.writeString(createdAt.toString());
    }
}
