package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.ParentRecorderView;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ClickEvent;
import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 4/3/18.
 */

public class RecorderCheckBox extends AppCompatCheckBox implements ParentRecorderView {
    GestureDetector checkBoxGestureDetector;
    String viewId;
    XMLCreator xmlCreator;
    private void init(Context context) {
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
        checkBoxGestureDetector = new GestureDetector(context, new RecorderCheckBox.CheckBoxGestureListener());
        /*this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                checkBoxGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });*/
    }
    @Override
    public String getViewId() {
        return viewId;
    }
    public RecorderCheckBox(Context context) {
        super(context);
        init(context);
    }
    public RecorderCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    public RecorderCheckBox(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    private class CheckBoxGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderCheckBox.this);
            View listItemParentView = Utils.getParentListItemView(RecorderCheckBox.this);
            int itemId = Constants.NO_LIST_VIEW;
            if (parentListView != null)
                itemId = parentListView.getPositionForView(listItemParentView);
            ClickEvent clickEvent = new ClickEvent(viewId, Constants.CHECKBOX_EVENT_TYPE_ATTRIBUTE,
                    Utils.getViewIdStringFromView(parentListView), itemId,  xmlCreator);
            clickEvent.saveEvent();
            //performClick();
            return true;
        }
    }
}