package com.ie23s.android.suicidewarehouse.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ie23s.android.suicidewarehouse.R;

public class NotificationUtil {
    private final String NOTIFICATION_TAG;
    private final String CHANEL_ID;
    private final Context context;
    private final NotificationManager notificationManager;
    private final int id;

    private Notification.Builder builder;

    private Notification notification;

    public NotificationUtil(final Context context, final String tag, final String channel_id,
                            int id) {
        this.context = context;
        NOTIFICATION_TAG = tag;
        CHANEL_ID = channel_id;
        this.id = id;
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel("description");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel(String description) {
        if (notificationManager.getNotificationChannel(CHANEL_ID) != null)
            return;

        NotificationChannel mChannel = new NotificationChannel(CHANEL_ID,
                NOTIFICATION_TAG, NotificationManager.IMPORTANCE_HIGH);
        mChannel.setDescription(description);

        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);


        mChannel.enableVibration(false);
        //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(mChannel);

    }

    public void create(String title, String text) {
        builder = new Notification.Builder(context);

//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(context,
//                0, notificationIntent,
//                PendingIntent.FLAG_CANCEL_CURRENT);

        //.setContentIntent(contentIntent)
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                // большая картинка
                //.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_action_stat_reply))
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                //.setTicker("Последнее китайское предупреждение!")
                //.setWhen(System.currentTimeMillis())
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle(title)
                //.setOngoing(true)
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText(text)

                .setOnlyAlertOnce(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANEL_ID);
        }
        build();
    }

    public void sendNotify() {
        notificationManager.notify(id, notification);
    }

    public void cancel() {
        notificationManager.cancel(NOTIFICATION_TAG, id);
    }

    public Notification getNotification() {
        return notification;
    }

    private void build() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
    }

    public void updateNotify(String text) {
        builder.setContentText(text);
        build();
        notification.defaults = 0;
        sendNotify();
    }

}
