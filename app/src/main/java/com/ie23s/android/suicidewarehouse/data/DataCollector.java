package com.ie23s.android.suicidewarehouse.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ie23s.android.suicidewarehouse.MyIntentService;

import java.io.Serializable;

public class DataCollector {
    private final Context context;
    private final Intent intent;
    private final IntentFilter filter;
    private Receiver receiver;
    private volatile boolean hasData = false;
    private volatile Data data;
    private BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            data = (Data) intent.getSerializableExtra("DATA");
            hasData = true;
            if (receiver != null)
                receiver.onReceive(data);
        }
    };

    public DataCollector(final Context context, final boolean isService) {
        this.context = context;

        intent = new Intent(isService ? MyIntentService.ACTION_SEND : MyIntentService.ACTION_GET);
        filter = new IntentFilter(isService ? MyIntentService.ACTION_GET : MyIntentService.ACTION_SEND);

        receiver = null;

    }

    public DataCollector(final Context context, final boolean isService, final Receiver receiver) {
        this.context = context;

        intent = new Intent(isService ? MyIntentService.ACTION_SEND : MyIntentService.ACTION_GET);
        filter = new IntentFilter(isService ? MyIntentService.ACTION_GET : MyIntentService.ACTION_SEND);

        this.receiver = receiver;
    }

    public void registerReceiver() {
        context.registerReceiver(mScreenStateReceiver, filter);

    }

    public void unregisterReceiver() {
        try {
            context.unregisterReceiver(mScreenStateReceiver);
        } catch (Exception ignored) {
        }

    }

    public boolean hasData() {
        return hasData;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public Data next() {
        while (!hasData) ;
        hasData = false;
        return data;
    }

    public void sendData(Data data) {
        intent.removeExtra("DATA");
        intent.putExtra("DATA", data);

        context.sendBroadcast(intent);
    }

    public Data getData() {
        hasData = false;
        return data;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public interface Receiver {
        void onReceive(Data data);
    }

    public static class Data implements Serializable {
        private int status;
        private String data;

        public Data(int status, String data) {
            this.status = status;
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public String getData() {
            return data;
        }
    }

}
