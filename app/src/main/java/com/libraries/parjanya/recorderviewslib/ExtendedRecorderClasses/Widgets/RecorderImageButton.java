package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
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

public class RecorderImageButton extends AppCompatImageButton implements ParentRecorderView {
    GestureDetector imageButtonGestureDetector;
    String viewId;
    XMLCreator xmlCreator;
    private void init(Context context) {
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
        imageButtonGestureDetector = new GestureDetector(context, new ImageButtonGestureListener());
    }

    @Override
    public String getViewId() {
        return viewId;
    }

    public RecorderImageButton(Context context) {
        super(context);
        init(context);
    }
    public RecorderImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    public RecorderImageButton(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        imageButtonGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private class ImageButtonGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderImageButton.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderImageButton.this);
            View listItemParentView = Utils.getParentListItemView(RecorderImageButton.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderImageButton.this);
            int itemId = Constants.NO_LIST_VIEW;
            String parentId = Constants.NO_ID;

            if (parentListView != null) {
                parentId = Utils.getViewIdStringFromView(parentListView);
                itemId = parentListView.getPositionForView(listItemParentView);
            }
            else if (parentRecyclerView != null) {
                parentId = Utils.getViewIdStringFromView(parentRecyclerView);
                itemId = parentRecyclerView.getLayoutManager().getPosition(recyclerItemView);
            }

            ClickEvent clickEvent = new ClickEvent(viewId, Constants.BUTTON_EVENT_TYPE_ATTRIBUTE, parentId, itemId,  xmlCreator);
            clickEvent.saveEvent();
            //performClick();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderImageButton.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderImageButton.this);
            View listItemParentView = Utils.getParentListItemView(RecorderImageButton.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderImageButton.this);
            int itemId = Constants.NO_LIST_VIEW;
            String parentId = Constants.NO_ID;

            if (parentListView != null) {
                parentId = Utils.getViewIdStringFromView(parentListView);
                itemId = parentListView.getPositionForView(listItemParentView);
            }
            else if (parentRecyclerView != null) {
                parentId = Utils.getViewIdStringFromView(parentRecyclerView);
                itemId = parentRecyclerView.getLayoutManager().getPosition(recyclerItemView);
            }

            LongPressEvent longPressEvent = new LongPressEvent(viewId, Constants.LONG_PRESS_EVENT_TAG_NAME, parentId, itemId,  xmlCreator);
            longPressEvent.saveEvent();
            //performLongClick();
        }
    }
}
