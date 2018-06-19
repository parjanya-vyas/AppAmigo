package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckedTextView;
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

public class RecorderCheckedTextView extends AppCompatCheckedTextView implements ParentRecorderView {
    GestureDetector textViewGestureDetector;
    String viewId;
    XMLCreator xmlCreator;
    private void init(Context context) {
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
        textViewGestureDetector = new GestureDetector(context, new RecorderCheckedTextView.CheckedTextViewGestureListener());
    }
    @Override
    public String getViewId() {
        return viewId;
    }
    public RecorderCheckedTextView(Context context) {
        super(context);
        init(context);
    }
    public RecorderCheckedTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    public RecorderCheckedTextView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        textViewGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private class CheckedTextViewGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderCheckedTextView.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderCheckedTextView.this);
            View listItemParentView = Utils.getParentListItemView(RecorderCheckedTextView.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderCheckedTextView.this);
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
            ListView parentListView = Utils.getParentListView(RecorderCheckedTextView.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderCheckedTextView.this);
            View listItemParentView = Utils.getParentListItemView(RecorderCheckedTextView.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderCheckedTextView.this);
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
