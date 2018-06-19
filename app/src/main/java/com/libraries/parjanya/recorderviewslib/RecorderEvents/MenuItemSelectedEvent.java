package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Activities.ParentActivity;
import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

public class MenuItemSelectedEvent extends RecorderEvent {
    public String itemId;
    public MenuItemSelectedEvent(String eventType, XMLCreator xmlCreator, String itemId) {
        super(Constants.NO_ID, eventType, Constants.NO_ID, Constants.NO_LIST_VIEW, xmlCreator);
        this.itemId = itemId;
    }

    @Override
    public void saveEvent() {
        if (Utils.isRecordingEnabled()) {
            switch (Utils.getRecordingMode()) {
                case Constants.RECORDING_MODE_OFFLINE:
                    xmlCreator.appendMenuItemSelectedEvent(this);
                    break;
                case Constants.RECORDING_MODE_ONLINE:
                    NetworkUtils.sendToPeer(Utils.serializeEvent(this));
            }
        }
    }

    @Override
    public void playEvent(final Activity recorderActivity) {
        final int integerItemId = Utils.getViewIdIntFromString(itemId, recorderActivity);
        recorderActivity.onOptionsItemSelected(((ParentActivity)recorderActivity).getCurMenu().findItem(integerItemId));
    }
}
