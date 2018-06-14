package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.ParentRecorderView;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ClickEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.LongPressEvent;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

public class RecorderImageView extends AppCompatImageView implements ParentRecorderView {
    GestureDetector imageViewGestureDetector;
    String viewId;
    XMLCreator xmlCreator;
    private void init(final Context context) {
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
        imageViewGestureDetector = new GestureDetector(context, new ImageViewGestureListener());
        /*this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imageViewGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView parentListView = Utils.getParentListView(RecorderImageView.this);
                RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderImageView.this);
                View listItemParentView = Utils.getParentListItemView(RecorderImageView.this);
                View recyclerItemView = Utils.getParentRecyclerItemView(RecorderImageView.this);
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
            }
        });*/
    }

    @Override
    public String getViewId() {
        return viewId;
    }

    public RecorderImageView(Context context) {
        super(context);
        init(context);
    }
    public RecorderImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    public RecorderImageView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        imageViewGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private class ImageViewGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderImageView.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderImageView.this);
            View listItemParentView = Utils.getParentListItemView(RecorderImageView.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderImageView.this);
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
            ListView parentListView = Utils.getParentListView(RecorderImageView.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderImageView.this);
            View listItemParentView = Utils.getParentListItemView(RecorderImageView.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderImageView.this);
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
