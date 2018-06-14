package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.ParentRecorderView;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ClickEvent;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

public class RecorderRadioButton extends AppCompatRadioButton implements ParentRecorderView {
    GestureDetector radioButtonGestureDetector;
    String viewId;
    XMLCreator xmlCreator;
    private void init(Context context) {
        xmlCreator = new XMLCreator(context);
        viewId = Utils.getViewIdStringFromView(this);
        radioButtonGestureDetector = new GestureDetector(context, new RecorderRadioButton.RadioButtonGestureListener());
        /*this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                radioButtonGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });*/
    }
    @Override
    public String getViewId() {
        return viewId;
    }
    public RecorderRadioButton(Context context) {
        super(context);
        init(context);
    }
    public RecorderRadioButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    public RecorderRadioButton(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        radioButtonGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private class RadioButtonGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ListView parentListView = Utils.getParentListView(RecorderRadioButton.this);
            RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderRadioButton.this);
            View listItemParentView = Utils.getParentListItemView(RecorderRadioButton.this);
            View recyclerItemView = Utils.getParentRecyclerItemView(RecorderRadioButton.this);
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

            ClickEvent clickEvent = new ClickEvent(viewId, Constants.CHECKBOX_EVENT_TYPE_ATTRIBUTE, parentId, itemId, xmlCreator);
            clickEvent.saveEvent();
            //performClick();
            return true;
        }
    }
}
