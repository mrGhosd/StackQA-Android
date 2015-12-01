package com.vsokoltsov.stackqa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vsokoltsov on 02.12.15.
 */
public class AppStartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // do startup tasks or start your luncher activity
        Intent newIntent = new Intent(context, StartedService.class);
        context.startService(newIntent);
    }
}
