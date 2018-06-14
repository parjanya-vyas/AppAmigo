package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.ParentRecorderView;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.TextChangedEvent;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

public class RecorderTextInputEditText extends TextInputEditText implements ParentRecorderView {
    XMLCreator xmlCreator;
    String viewId;
    @Override
    public String getViewId() {
        return viewId;
    }
    void init() {
        xmlCreator = new XMLCreator(getContext());
        viewId = Utils.getViewIdStringFromView(this);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ListView parentListView = Utils.getParentListView(RecorderTextInputEditText.this);
                RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderTextInputEditText.this);
                View listItemParentView = Utils.getParentListItemView(RecorderTextInputEditText.this);
                View recyclerItemView = Utils.getParentRecyclerItemView(RecorderTextInputEditText.this);
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

                TextChangedEvent textChangedEvent = new TextChangedEvent(charSequence.toString(), viewId,
                        parentId, itemId, Constants.EDIT_TEXT_EVENT_TYPE_ATTRIBUTE, xmlCreator);
                textChangedEvent.saveEvent();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public RecorderTextInputEditText(Context context) {
        super(context);
        init();
    }
    public RecorderTextInputEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }
    public RecorderTextInputEditText(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init();
    }
}
