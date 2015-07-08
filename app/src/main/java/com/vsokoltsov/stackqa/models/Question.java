package com.vsokoltsov.stackqa.models;

/**
 * Created by vsokoltsov on 06.07.15.
 */
import java.util.ArrayList;

public class Question {
    private String title;
    private int rate;

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
}
