package com.libraries.parjanya.recorderviewslib.NetworkAsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.R;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;

import java.lang.ref.WeakReference;
import java.net.Socket;

public class ServerAsyncTask extends AsyncTask {

    private WeakReference<Context> context;
    private AlertDialog alertDialog;

    public ServerAsyncTask(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context.get());
        View containerLayout = LayoutInflater.from(context.get()).inflate(R.layout.request_connect_dialog,null);
        containerLayout.findViewById(R.id.dialog_request_layout).setVisibility(View.VISIBLE);
        containerLayout.findViewById(R.id.dialog_connect_layout).setVisibility(View.GONE);
        TextView myIPTextView = containerLayout.findViewById(R.id.dialog_request_message_text_view);
        myIPTextView.setText(context.get().getString(R.string.dialog_request_message)+ NetworkUtils.getIPAddress(true));
        builder.setTitle(R.string.dialog_request_title);
        builder.setView(containerLayout);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        context.get().sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return NetworkUtils.startServer();
    }

    @Override
    protected void onPostExecute(Object sock) {
        NetworkUtils.startListening((Socket) sock, context.get());
        Utils.getCurrentActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        alertDialog.dismiss();
    }
}
