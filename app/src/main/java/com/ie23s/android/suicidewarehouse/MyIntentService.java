package com.ie23s.android.suicidewarehouse;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.ie23s.android.suicidewarehouse.io.ConnectionUtil;
import com.ie23s.android.suicidewarehouse.io.SocketAsync;
import com.ie23s.android.suicidewarehouse.utils.NotificationUtil;

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
    public static final String ACTION_GET = "com.ie23s.android.suicidewarehouse.action.GET";
    public static final String ACTION_SEND = "com.ie23s.android.suicidewarehouse.action.SEND";
    public static final String CHANNEL_ID = "ForegroundServiceChannels";

    NotificationUtil notificationUtil;
    ConnectionUtil connectionUtil;
    SocketAsync socketAsync;

    BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            notificationUtil.updateNotify(intent.getStringExtra("name"));
            //startForeground(1,notificationUtil.getNotification());
        }
    };

    public MyIntentService() {
        super("MyIntentService");
    }
    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter(ACTION_GET);
        registerReceiver(mScreenStateReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenStateReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        notificationUtil = new NotificationUtil(this,
               "TAG!!!", "TEST_NOTIFY", 1);
        notificationUtil.create("Test", "Connecting...");

        connectionUtil = new ConnectionUtil();

        socketAsync = new SocketAsync(new IncomingHandler(this), connectionUtil,
                notificationUtil);
        socketAsync.execute();

        startForeground(1, notificationUtil.getNotification());

        return START_NOT_STICKY;
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
