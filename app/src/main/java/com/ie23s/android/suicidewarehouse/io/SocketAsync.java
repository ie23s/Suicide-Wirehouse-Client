package com.ie23s.android.suicidewarehouse.io;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.ie23s.android.suicidewarehouse.MyIntentService;
import com.ie23s.android.suicidewarehouse.utils.NotificationUtil;

import java.io.IOException;
import java.net.Socket;

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

        Intent intent = new Intent(MyIntentService.ACTION_SEND);
        for (boolean c = true; c; ) {
            try {
                String data = new String(connectionUtil.getData());
                intent.putExtra("SERVER_STATUS", "OK");
                intent.putExtra("VALUE", data);
            } catch (IOException e) {
                intent.putExtra("SERVER_STATUS", "FATAL_ERROR");
                connectionUtil.close();
                c = false;
            } catch (UnsignedExeption e) {
                intent.putExtra("SERVER_STATUS", "ERROR_" + e.getCode());
            }

            Message threadMessage = new Message();
            threadMessage.obj = intent;
            threadHandler.sendMessage(threadMessage);

        }
        return null;
    }

    private void send(Socket socket) {

    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);


    }
}