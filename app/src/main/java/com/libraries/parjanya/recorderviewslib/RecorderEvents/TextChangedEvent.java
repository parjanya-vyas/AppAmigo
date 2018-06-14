package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.PlayerRunnable;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets.RecorderEditText;
import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 3/3/18.
 */

public class TextChangedEvent extends RecorderEvent {
    public String changedText;

    public TextChangedEvent(String changedText, String viewId, String parentListViewId, int listViewItemId, String eventType, XMLCreator xmlCreator) {
        super(viewId, eventType, parentListViewId, listViewItemId, xmlCreator);
        this.changedText = changedText;
    }

    @Override
    public void saveEvent() {
        if (Utils.isRecordingEnabled()) {
            switch (Utils.getRecordingMode()) {
                case Constants.RECORDING_MODE_OFFLINE:
                    xmlCreator.appendTextChangedEvent(this);
                    break;
                case Constants.RECORDING_MODE_ONLINE:
                    NetworkUtils.sendToPeer(Utils.serializeEvent(this));
            }
        }
    }

    @Override
    public void playEvent(final Activity recorderActivity) {
        PlayerRunnable editTextRunnable = new PlayerRunnable() {
            @Override
            public void run() {
                TextView editableText = (TextView)targetView;
                editableText.setText(changedText);
            }
        };

        if (listViewItemId != Constants.NO_LIST_VIEW)
            Utils.getViewFromParentListViewAndRun(viewId, parentListViewId, recorderActivity, listViewItemId, editTextRunnable);
        else {
            View editableTextView = recorderActivity.findViewById(Utils.getViewIdIntFromString(viewId, recorderActivity));
            editTextRunnable.setTargetView(editableTextView);
            editTextRunnable.run();
        }
    }
}