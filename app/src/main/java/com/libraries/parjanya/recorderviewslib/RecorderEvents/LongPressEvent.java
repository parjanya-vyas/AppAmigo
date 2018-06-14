package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.PlayerRunnable;
import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 3/3/18.
 */

public class LongPressEvent extends RecorderEvent {

    public LongPressEvent(String viewId, String eventType, String parentListViewId, int listViewItemId, XMLCreator xmlCreator) {
        super(viewId, eventType, parentListViewId, listViewItemId, xmlCreator);
    }

    @Override
    public void saveEvent() {
        if (Utils.isRecordingEnabled()) {
            switch (Utils.getRecordingMode()) {
                case Constants.RECORDING_MODE_OFFLINE:
                    xmlCreator.appendLongPressEvent(this);
                    break;
                case Constants.RECORDING_MODE_ONLINE:
                    NetworkUtils.sendToPeer(Utils.serializeEvent(this));
            }
        }
    }

    @Override
    public void playEvent(Activity recorderActivity) {
        PlayerRunnable longPressRunnable = new PlayerRunnable() {
            @Override
            public void run() {
                targetView.performLongClick();
            }
        };

        if (listViewItemId != Constants.NO_LIST_VIEW)
            Utils.getViewFromParentListViewAndRun(viewId, parentListViewId, recorderActivity, listViewItemId, longPressRunnable);
        else {
            View view = recorderActivity.findViewById(Utils.getViewIdIntFromString(viewId, recorderActivity));
            longPressRunnable.setTargetView(view);
            longPressRunnable.run();
        }
    }
}
