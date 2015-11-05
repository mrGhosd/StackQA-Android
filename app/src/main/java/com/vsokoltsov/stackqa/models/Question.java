package com.vsokoltsov.stackqa.models;
import android.os.Parcel;
import android.os.Parcelable;

import com.vsokoltsov.stackqa.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by vsokoltsov on 06.07.15.
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Question implements Parcelable{
    private int id;
    private String title;
    private int rate;
    private Category category;
    private User user;
    private Date createdAt;
    private int answersCount;
    private int commentsCount;
    private int views;
    private String text;
    private String tags;

    public Question(){

    }
    public Question(JSONObject object){
        try {
            setID(object.getInt("id"));
            setTitle(object.getString("title"));
            if (object.has("text")) setText(object.getString("text"));
            if (object.has("user")) setUser(object.getJSONObject("user"));
            if (object.has("tag_list")) setTags(object.getString("tag_list"));
            setRate(object.getInt("rate"));
            setCategory(object.getJSONObject("category"));
            setCreatedAt(object.getString("created_at"));
            setAnswersCount(object.getInt("answers_count"));
            setCommentsCount(object.getInt("comments_count"));
            setViews(object.getInt("views"));

        } catch (JSONException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    private Question(Parcel in) {
        setID(in.readInt());
    }

    public int getID(){ return id; }

    public void setID(int id){ this.id = id; }

    public Question(String title){
        setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(JSONObject category){
        this.category = new Category(category);
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

    public void setAnswersCount(int count){
        this.answersCount = count;
    }

    public void setCommentsCount(int count){
        this.answersCount = count;
    }

    public void setViews(int views){
        this.answersCount = views;
    }

    public int getAnswersCount(){
        return this.answersCount;
    }

    public int getCommentsCount(){
        return this.commentsCount;
    }

    public int getViews(){
        return this.views;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public void setTags(String tags){
        this.tags = tags;
    }

    public String getTags(){
        return this.tags;
    }

    public void setUser(JSONObject user){
        this.user = new User(user);
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            Question question = new Question();
            question.title = in.readString();
            question.id = in.readInt();
            question.rate = in.readInt();
            question.answersCount = in.readInt();
            question.commentsCount = in.readInt();
            question.views = in.readInt();
            try {
                question.setCreatedAt(in.readString());
            } catch (ParseException e){
                e.printStackTrace();
            }
            return question;
        }
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(id);
        dest.writeInt(rate);
        dest.writeInt(answersCount);
        dest.writeInt(commentsCount);
        dest.writeInt(views);
        dest.writeString(createdAt.toString());
    }

    public static void getCollection(){

    }
}
