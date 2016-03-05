package com.vsokoltsov.stackqa.models;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.QuestionMessage;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 06.07.15.
 */

public class Question implements Parcelable, RequestCallbacks{
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
    private Vote currentUserVote;
    private boolean isClosed;

    public Question(){

    }
    public Question(JSONObject object){
        try {
            setID(object.getInt("id"));
            setTitle(object.getString("title"));
            if (object.has("text")) setText(object.getString("text"));
            if (object.has("user")) setUser(object.getJSONObject("user"));
            if (object.has("tag_list")) setTags(object.getString("tag_list"));
            if (object.has("current_user_voted") && !object.isNull("current_user_voted")) {
                setCurrentUserVote(object.getJSONObject("current_user_voted"));
            }
            setRate(object.getInt("rate"));
            setCategory(object.getJSONObject("category"));
            setCreatedAt(object.getString("created_at"));
            setAnswersCount(object.getInt("answers_count"));
            setCommentsCount(object.getInt("comments_count"));
            setViews(object.getInt("views"));
            if (object.has("is_closed")) setIsClosed(object.getBoolean("is_closed"));

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
            question.createdAt = (Date) in.readSerializable();
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
        dest.writeSerializable(createdAt);
    }

    public void getCollection(){
        String url = AppController.APP_HOST + "/api/v1/questions";
        ApiRequest.getInstance(this).get(url, "list", null);
    }

    public void get(int id){
        String url = AppController.APP_HOST + "/api/v2/questions" + id;
        ApiRequest.getInstance(this).get(url, "detail", null);
    }

    @Override
    public void successCallback(String requestName, JSONObject object) {
        EventBus.getDefault().post(new QuestionMessage(requestName, object));
    }

    @Override
    public void failureCallback(String requestName, VolleyError error) {
        EventBus.getDefault().post(new QuestionMessage(requestName, error));
    }

    public Vote getCurrentUserVote() {
        return currentUserVote;
    }

    public void setCurrentUserVote(JSONObject vote) {
        if (vote != null) {
            this.currentUserVote = new Vote(vote);
        }
    }

    public void setCurrentUserVote(Vote vote) {
        this.currentUserVote = vote;
    }

    public void setCurrentUserVote() {
        this.currentUserVote = null;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
}
