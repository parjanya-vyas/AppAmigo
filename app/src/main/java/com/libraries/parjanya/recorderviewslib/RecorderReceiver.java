package com.libraries.parjanya.recorderviewslib;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.libraries.parjanya.recorderviewslib.NetworkAsyncTasks.ClientAsyncTask;
import com.libraries.parjanya.recorderviewslib.NetworkAsyncTasks.ServerAsyncTask;
import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLParser;

public class RecorderReceiver extends BroadcastReceiver {

    Activity currentActivity;
    AlertDialog alertDialog;


    public RecorderReceiver(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent==null || intent.getAction()==null)
            return;
        switch (intent.getAction()) {
            case Constants.ACTION_START_SERVER:
                new ServerAsyncTask(context).execute();
                break;
            case Constants.ACTION_CONNECT_CLIENT:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View containerLayout = LayoutInflater.from(context).inflate(R.layout.request_connect_dialog,null);
                containerLayout.findViewById(R.id.dialog_connect_layout).setVisibility(View.VISIBLE);
                containerLayout.findViewById(R.id.dialog_request_layout).setVisibility(View.GONE);
                containerLayout.findViewById(R.id.dialog_connect_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String serverIP = ((EditText)containerLayout.findViewById(R.id.dialog_server_ip_edit_text)).getText().toString();
                        new ClientAsyncTask(alertDialog).execute(new String[]{serverIP});
                    }
                });
                builder.setTitle(R.string.dialog_connect_title);
                builder.setView(containerLayout);
                alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
                break;
            case Constants.ACTION_DISCONNECT_CLIENT:
                NetworkUtils.disconnectClient();
                Utils.initializeNotifications(context, false);
                break;
            case Constants.ACTION_START_RECORDING:
                Utils.setRecordingState(true);
                if (!Utils.isRootTagStarted()) {
                    new XMLCreator(context).appendStartRootElement();
                    Utils.setRootTagStarted(true);
                }
                Utils.initializeNotifications(context, false);
                break;
            case Constants.ACTION_STOP_RECORDING:
                Utils.setRecordingState(false);
                if (!Utils.isRootTagEnded()) {
                    new XMLCreator(context).appendEndRootElement();
                    Utils.setRootTagEnded(true);
                }
                Utils.initializeNotifications(context, false);
                break;
            case Constants.ACTION_START_PLAYING:
                new XMLParser(currentActivity);
                break;
            case Constants.ACTION_CHANGE_MODE:
                Utils.toggleRecordingMode();
                Utils.initializeNotifications(context, false);
        }
    }
}
