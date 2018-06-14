package com.libraries.parjanya.recorderviewslib.NetworkAsyncTasks;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;

import java.net.Socket;

public class ClientAsyncTask extends AsyncTask {

    private AlertDialog alertDialog;

    public ClientAsyncTask(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String serverIP = ((String[])objects)[0];
        return NetworkUtils.connectToServer(serverIP);
    }

    @Override
    protected void onPostExecute(final Object sock) {
        NetworkUtils.initializeOutputChannel((Socket)sock);
        Utils.setRecordingState(true);
        Utils.initializeNotifications(alertDialog.getContext(), false);
        alertDialog.dismiss();
    }
}
