package com.ie23s.android.suicidewarehouse.io;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.ie23s.android.suicidewarehouse.data.DataCollector;
import com.ie23s.android.suicidewarehouse.utils.NotificationUtil;

import java.io.IOException;

public class SocketAsync extends AsyncTask<Void, Integer, Void> {

    private Handler threadHandler;
    private ConnectionUtil connectionUtil;
    private NotificationUtil notificationUtil;

    public SocketAsync(Handler threadHandler, ConnectionUtil connectionUtil,
                       NotificationUtil notificationUtil) {
        this.threadHandler = threadHandler;
        this.connectionUtil = connectionUtil;
        this.notificationUtil = notificationUtil;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (connectionUtil.openConnection() == 0) {
            notificationUtil.updateNotify("Connected!");
        } else {

            notificationUtil.updateNotify("Failed!");
            return null;
        }

        for (boolean c = true; c; ) {
            DataCollector.Data data;
            try {
                data = new DataCollector.Data(200, new String(connectionUtil.getData()));
            } catch (IOException e) {
                data = new DataCollector.Data(500, null);
                connectionUtil.close();
                c = false;
            } catch (UnsignedExeption e) {
                data = new DataCollector.Data(501, "ERROR_" + e.getCode());
            }

            Message threadMessage = new Message();
            threadMessage.obj = data;
            threadHandler.sendMessage(threadMessage);

        }
        return null;
    }
}