package com.ie23s.android.suicidewarehouse.io;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.ie23s.android.suicidewarehouse.data.DataCollector;

import java.io.IOException;

public class SocketQuery extends AsyncTask<String, Integer, Void> {
    private Handler threadHandler;
    private ConnectionUtil connectionUtil;

    public SocketQuery(Handler threadHandler, ConnectionUtil connectionUtil) {
        this.threadHandler = threadHandler;
        this.connectionUtil = connectionUtil;

    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This will normally run on a background thread. But to better
     * support testing frameworks, it is recommended that this also tolerates
     * direct execution on the foreground thread, as part of the {@link #execute} call.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param args The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(String... args) {

        try {
            connectionUtil.sendData(args[0]);
        } catch (IOException e) {
            handleMessage(new DataCollector.Data(500, null));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return null;
    }

    private void handleMessage(DataCollector.Data data) {

        Message threadMessage = new Message();
        threadMessage.obj = data;
        threadHandler.sendMessage(threadMessage);
    }
}
