package com.vsokoltsov.stackqa.models;

/**
 * Created by vsokoltsov on 01.10.15.
 */
public class NavigationItem {
    private int image;
    private String title;
    public NavigationItem(int image, String title){
        this.image = image;
        this.title = title;
    }

    public int getImage(){
        return this.image;
    }
    public String getTitle(){
        return this.title;
    }
}
