package com.ie23s.android.suicidewarehouse;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION = "com.ie23s.android.myapplication.action.FOO";
    public static final String CHANNEL_ID = "ForegroundServiceChannels";

    //SocketAsync d;

    public MyIntentService() {
        super("MyIntentService");
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NewMessageNotification  newMessageNotification = new NewMessageNotification(this,
               "TAG!!!", "TEST_NOTIFY", 1);
        newMessageNotification.create("Test", "Just stupid test");
////        newMessageNotification.sendNotify();
////
        Log.d("Service", "Service started");
//////        d = new SocketAsync(new IncomingHandler(this), newMessageNotification);
//////        d.execute();
        startForeground(1,newMessageNotification.getNotification());

        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    static class IncomingHandler extends Handler {
        private final WeakReference<MyIntentService> mService;

        IncomingHandler(MyIntentService service) {
            mService = new WeakReference<>(service);
        }
        @Override
        public void handleMessage( Message msg)
        {
            MyIntentService service = mService.get();
            if (service != null) {
                service.sendBroadcast((Intent) msg.obj);

                super.handleMessage(msg);
            }
        }
    }
}
