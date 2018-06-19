package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ViewPagerItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 9/3/18.
 */

public class RecorderViewPager extends ViewPager implements ParentRecorderView {
    String viewId;
    XMLCreator xmlCreator;
    OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ViewPagerItemSelectedEvent viewPagerItemSelectedEvent = new ViewPagerItemSelectedEvent(viewId, Constants.VIEW_PAGER_EVENT_TYPE_ATTRIBUTE, xmlCreator, position);
            viewPagerItemSelectedEvent.saveEvent();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public String getViewId() {
        return viewId;
    }
    public RecorderViewPager(Context context) {
        super(context);
        addOnPageChangeListener(onPageChangeListener);
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
    }
    public RecorderViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        addOnPageChangeListener(onPageChangeListener);
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
    }
}
