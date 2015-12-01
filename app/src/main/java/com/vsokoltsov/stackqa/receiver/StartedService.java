package com.vsokoltsov.stackqa.receiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by vsokoltsov on 02.12.15.
 */
public class StartedService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startid) {

    }
}
