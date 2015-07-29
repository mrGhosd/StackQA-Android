package com.vsokoltsov.stackqa.models;
import com.vsokoltsov.stackqa.models.Category;
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

public class Question {
    private int id;
    private String title;
    private int rate;
    private Category category;
    private Date createdAt;
    private int answersCount;
    private int commentsCount;
    private int views;

    public Question(JSONObject object){
        try {
            setTitle(object.getString("title"));
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
}
