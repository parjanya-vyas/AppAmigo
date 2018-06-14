package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
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

public class RecorderSwitch extends SwitchCompat implements ParentRecorderView {
    GestureDetector switchGestureDetector;
    XMLCreator xmlCreator;
    String viewId;
    private void init(Context context) {
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
        switchGestureDetector = new GestureDetector(context, new RecorderSwitch.SwitchGestureListener());
        /*this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switchGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });*/
    }
    @Override
    public String getViewId() {
        return viewId;
    }
    public RecorderSwitch(Context context) {
        super(context);
        init(context);
    }
    public RecorderSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    public RecorderSwitch(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switchGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private class SwitchGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderSwitch.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderSwitch.this);
            View listItemParentView = Utils.getParentListItemView(RecorderSwitch.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderSwitch.this);
            int listViewItemId = Constants.NO_LIST_VIEW;
            String parentId = Constants.NO_ID;

            if (parentListView != null) {
                parentId = Utils.getViewIdStringFromView(parentListView);
                listViewItemId = parentListView.getPositionForView(listItemParentView);
            }
            else if (parentRecyclerView != null) {
                parentId = Utils.getViewIdStringFromView(parentRecyclerView);
                listViewItemId = parentRecyclerView.getLayoutManager().getPosition(recyclerItemView);
            }

            ClickEvent clickEvent = new ClickEvent(viewId, Constants.SWITCH_EVENT_TYPE_ATTRIBUTE,
                    Utils.getViewIdStringFromView(parentListView), listViewItemId, xmlCreator);
            clickEvent.saveEvent();
            //performClick();
            return true;
        }
    }
}
