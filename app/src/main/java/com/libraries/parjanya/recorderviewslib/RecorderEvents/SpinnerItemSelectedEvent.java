package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.PlayerRunnable;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets.RecorderSpinner;
import com.libraries.parjanya.recorderviewslib.Utils.NetworkUtils;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;


/**
 * Created by parjanya on 10/3/18.
 */

public class SpinnerItemSelectedEvent extends RecorderEvent {
    public int itemId;

    public SpinnerItemSelectedEvent(String viewId, String eventType, String parentListViewId, int listViewItemId, XMLCreator xmlCreator, int itemId) {
        super(viewId, eventType, parentListViewId, listViewItemId, xmlCreator);
        this.itemId = itemId;
    }

    @Override
    public void saveEvent() {
        if (Utils.isRecordingEnabled()) {
            switch (Utils.getRecordingMode()) {
                case Constants.RECORDING_MODE_OFFLINE:
                    xmlCreator.appendSpinnerItemSelectedEvent(this);
                    break;
                case Constants.RECORDING_MODE_ONLINE:
                    NetworkUtils.sendToPeer(Utils.serializeEvent(this));
            }
        }
    }

    @Override
    public void playEvent(Activity recorderActivity) {
        PlayerRunnable spinnerItemSelectedRunnable = new PlayerRunnable() {
            @Override
            public void run() {
                final RecorderSpinner recorderSpinner = (RecorderSpinner)targetView;
                Handler handler = new Handler();
                recorderSpinner.performClick();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recorderSpinner.onDetachedFromWindow();
                        recorderSpinner.setSelection(itemId, true);
                    }
                }, Constants.PAUSE_TIME);
            }
        };
        ViewPager viewPager = recorderActivity.findViewById(Utils.getCurrentViewPagerId());
        View rootView;
        if (viewPager != null) {
            rootView = ((Fragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem())).getView();
        }
        else
            rootView = recorderActivity.getWindow().getDecorView().getRootView();
        if (listViewItemId != Constants.NO_LIST_VIEW)
            Utils.getViewFromParentListViewAndRun(viewId, parentListViewId, rootView, listViewItemId, spinnerItemSelectedRunnable);
        else {
            RecorderSpinner recorderSpinner = rootView.findViewById(Utils.getViewIdIntFromString(viewId, recorderActivity));
            spinnerItemSelectedRunnable.setTargetView(recorderSpinner);
            spinnerItemSelectedRunnable.run();
        }
    }
}
