package com.ie23s.android.suicidewarehouse;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.ie23s.android.suicidewarehouse.data.DataCollector;
import com.ie23s.android.suicidewarehouse.io.Auth;
import com.ie23s.android.suicidewarehouse.io.ConnectionUtil;
import com.ie23s.android.suicidewarehouse.io.SocketAsync;
import com.ie23s.android.suicidewarehouse.io.SocketQuery;
import com.ie23s.android.suicidewarehouse.utils.NotificationUtil;

import java.lang.ref.WeakReference;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService implements DataCollector.Receiver {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_GET = "com.ie23s.android.suicidewarehouse.action.GET";
    public static final String ACTION_SEND = "com.ie23s.android.suicidewarehouse.action.SEND";

    NotificationUtil notificationUtil;
    ConnectionUtil connectionUtil;
    SocketAsync socketAsync;
    SocketQuery socketQuery;
    DataCollector dataCollector;

    public MyIntentService() {
        super("MyIntentService");
    }
    @Override
    public void onCreate() {
        super.onCreate();

        dataCollector = new DataCollector(this.getApplicationContext(), true, this);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataCollector.unregisterReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Notification
        notificationUtil = new NotificationUtil(this,
               "TAG!!!", "TEST_NOTIFY", 1);
        notificationUtil.create("Test", "Connecting...");

        startForeground(1, notificationUtil.getNotification());

        dataCollector.registerReceiver();

        //Socket
        connectionUtil = new ConnectionUtil();

        socketAsync = new SocketAsync(new IncomingHandler(this), connectionUtil,
                notificationUtil);
        socketQuery = new SocketQuery(new IncomingHandler(this), connectionUtil);
        socketAsync.execute();

        return START_NOT_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public void onReceive(DataCollector.Data data) {

        switch (data.getStatus()) {
            case 101:
                String[] combination = data.getData().split(":");
                socketQuery.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                        Auth.encodePassword(combination[0], combination[1]));
                break;
        }

    }


    static class IncomingHandler extends Handler {
        private final WeakReference<MyIntentService> mService;

        IncomingHandler(MyIntentService service) {
            mService = new WeakReference<>(service);
        }
        @Override
        public void handleMessage(Message msg)
        {
            DataCollector dataCollector = mService.get().dataCollector;
            if (dataCollector != null) {

                dataCollector.sendData((DataCollector.Data) msg.obj);

                super.handleMessage(msg);
            }
        }
    }
}
