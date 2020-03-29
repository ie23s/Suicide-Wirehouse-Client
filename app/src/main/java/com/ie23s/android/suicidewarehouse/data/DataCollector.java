package com.ie23s.android.suicidewarehouse.data;

import android.content.Context;
import android.content.Intent;

import com.ie23s.android.suicidewarehouse.MyIntentService;

public class DataCollector {
    private final Context context;
    private final Intent intent;
    private boolean hasData = false;
    private String data;

    public DataCollector(final Context context) {
        this.context = context;
        intent = new Intent(MyIntentService.ACTION_GET);
    }

    public void sendData(String data) {
        intent.removeExtra("DATA");
        intent.putExtra("DATA", data);
        context.sendBroadcast(intent);
    }

    public void putData(String data) {
        this.data = data;
        hasData = true;
    }
    
    public boolean hasData() {
        return hasData;
    }

    public String getData() {
        hasData = false;
        return data;
    }

}
