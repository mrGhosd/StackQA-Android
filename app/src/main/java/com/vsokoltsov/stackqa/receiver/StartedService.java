package com.vsokoltsov.stackqa.receiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.vsokoltsov.stackqa.models.AuthManager;

import org.json.JSONException;

/**
 * Created by vsokoltsov on 02.12.15.
 */
public class StartedService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences pref = (SharedPreferences) getSharedPreferences("stackqa", Context.MODE_PRIVATE);
        String email = pref.getString("stackqaemail", null);
        String password = pref.getString("stackqapassword", null);
        if(email != null && password != null) {
            try {
                AuthManager.getInstance().signIn(email, password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.v("StartService", "StartService -- onCreate()");
        // do something when the service is created
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
}
