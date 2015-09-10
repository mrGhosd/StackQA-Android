package com.vsokoltsov.stackqa.models;

/**
 * Created by vsokoltsov on 03.07.15.
 */
public class ServerConnection {
    private static ServerConnection instance;

    private ServerConnection(){}

    public static synchronized ServerConnection getInstance(){
        if(instance == null){
            instance = new ServerConnection();
        }
        return instance;
    }
}
