package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 3/3/18.
 */

public class KeyPressEvent extends RecorderEvent {
    public int keyCode;

    public KeyPressEvent(int keyCode, String eventType, XMLCreator xmlCreator) {
        super(Constants.NO_ID, eventType, Constants.NO_ID, Constants.NO_LIST_VIEW, xmlCreator);
        this.keyCode = keyCode;
    }

    @Override
    public void saveEvent() {
        if (Utils.isRecordingEnabled()) {
            switch (Utils.getRecordingMode()) {
                case Constants.RECORDING_MODE_OFFLINE:
                    xmlCreator.appendKeyPressEvent(this);
                    break;
                case Constants.RECORDING_MODE_ONLINE:
                    NetworkUtils.sendToPeer(Utils.serializeEvent(this));
            }
        }
    }

    @Override
    public void playEvent(Activity recorderActivity) {
        //recorderActivity.onKeyDown(keyCode, new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
        View rootView = recorderActivity.getWindow().getDecorView().getRootView();
        rootView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
        rootView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
    }
}
