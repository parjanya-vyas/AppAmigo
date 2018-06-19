package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

public class ClickEvent extends RecorderEvent {

    public ClickEvent(String viewId, String eventType, String parentListViewId, int listViewItemId, XMLCreator xmlCreator) {
        super(viewId, eventType, parentListViewId, listViewItemId, xmlCreator);
    }

    @Override
    public void saveEvent() {
        if (Utils.isRecordingEnabled()) {
            switch (Utils.getRecordingMode()) {
                case Constants.RECORDING_MODE_OFFLINE:
                    xmlCreator.appendClickEvent(this);
                    break;
                case Constants.RECORDING_MODE_ONLINE:
                    NetworkUtils.sendToPeer(Utils.serializeEvent(this));
            }
        }
    }

    @Override
    public void playEvent(Activity recorderActivity) {
        PlayerRunnable clickRunnable = new PlayerRunnable() {
            @Override
            public void run() {
                targetView.performClick();
            }
        };
        ViewPager viewPager = recorderActivity.findViewById(Utils.getCurrentViewPagerId());
        View rootView;
        if (viewPager != null)
            rootView = ((Fragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem())).getView();
        else
            rootView = recorderActivity.getWindow().getDecorView().getRootView();
        if (listViewItemId != Constants.NO_LIST_VIEW)
            Utils.getViewFromParentListViewAndRun(viewId, parentListViewId, rootView, listViewItemId, clickRunnable);
        else {
            View view = rootView.findViewById(Utils.getViewIdIntFromString(viewId, recorderActivity));
            clickRunnable.setTargetView(view);
            clickRunnable.run();
        }
    }
}
