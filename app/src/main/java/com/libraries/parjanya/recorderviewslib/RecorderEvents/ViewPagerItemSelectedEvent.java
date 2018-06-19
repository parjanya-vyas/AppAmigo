package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import android.support.v4.view.ViewPager;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 9/3/18.
 */

public class ViewPagerItemSelectedEvent extends RecorderEvent {
    public int itemId;

    public ViewPagerItemSelectedEvent(String viewId, String eventType, XMLCreator xmlCreator, int itemId) {
        super(viewId, eventType, Constants.NO_ID, Constants.NO_LIST_VIEW, xmlCreator);
        this.itemId = itemId;
    }

    @Override
    public void saveEvent() {
        if (Utils.isRecordingEnabled()) {
            switch (Utils.getRecordingMode()) {
                case Constants.RECORDING_MODE_OFFLINE:
                    xmlCreator.appendViewPagerItemSelectedEvent(this);
                    break;
                case Constants.RECORDING_MODE_ONLINE:
                    NetworkUtils.sendToPeer(Utils.serializeEvent(this));
            }
        }
    }

    @Override
    public void playEvent(Activity recorderActivity) {
        ViewPager viewPager = recorderActivity.findViewById(Utils.getViewIdIntFromString(viewId, recorderActivity));
        viewPager.setCurrentItem(itemId, true);
        Utils.setCurrentViewPagerId(viewPager.getId());
    }
}
