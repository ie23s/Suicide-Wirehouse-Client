package com.ie23s.android.suicidewarehouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AutoStart extends BroadcastReceiver
{
    public void onReceive(Context context, Intent arg1)
    {
        //if (arg1.getAction() != null && arg1.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("Service", "Service started");
            Intent intent = new Intent(context, MyIntentService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
       // }
    }

}