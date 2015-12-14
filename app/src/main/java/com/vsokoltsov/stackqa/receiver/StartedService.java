package com.vsokoltsov.stackqa.receiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.vsokoltsov.stackqa.models.AuthManager;

import org.json.JSONException;

/**
 * Created by vsokoltsov on 02.12.15.
 */
public class StartedService extends Service {
    private Context context;
    private boolean isRunning;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
//        this.backgroundThread = new Thread(myTask);
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
        // do something when the service is created
    }

    private Runnable myTask = new Runnable() {
        public void run() {
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
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
//            this.backgroundThread.run();
        }
        return START_STICKY;
    }
}
