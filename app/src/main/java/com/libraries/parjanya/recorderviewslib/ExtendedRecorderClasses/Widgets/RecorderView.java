package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ClickEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.LongPressEvent;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

public class RecorderView extends View implements ParentRecorderView {
    GestureDetector viewGestureDetector;
    String viewId;
    XMLCreator xmlCreator;
    private void init(Context context) {
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
        viewGestureDetector = new GestureDetector(context, new ViewGestureListener());
    }
    @Override
    public String getViewId() {
        return viewId;
    }
    public RecorderView(Context context) {
        super(context);
        init(context);
    }
    public RecorderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    public RecorderView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }
    public RecorderView(Context context, AttributeSet attributeSet, int defStyleAttr, int defStyleRes) {
        super(context, attributeSet, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        viewGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private class ViewGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderView.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderView.this);
            View listItemParentView = Utils.getParentListItemView(RecorderView.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderView.this);
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

            ClickEvent clickEvent = new ClickEvent(viewId, Constants.TEXT_VIEW_EVENT_TYPE_ATTRIBUTE, parentId, listViewItemId, xmlCreator);
            clickEvent.saveEvent();
            //performClick();
            return true;
        }
        @Override
        public void onLongPress(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderView.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderView.this);
            View listItemParentView = Utils.getParentListItemView(RecorderView.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderView.this);
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

            LongPressEvent longPressEvent = new LongPressEvent(viewId, Constants.TEXT_VIEW_EVENT_TYPE_ATTRIBUTE, parentId, listViewItemId, xmlCreator);
            longPressEvent.saveEvent();
            super.onLongPress(e);
            //performLongClick();
        }
    }
}
