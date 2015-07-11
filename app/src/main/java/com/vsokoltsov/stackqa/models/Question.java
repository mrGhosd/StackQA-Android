package com.vsokoltsov.stackqa.models;
import com.vsokoltsov.stackqa.models.Category;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by vsokoltsov on 06.07.15.
 */
import java.util.ArrayList;

public class Question {
    private String title;
    private int rate;
    private Category category;

    public Question(){}

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
}
