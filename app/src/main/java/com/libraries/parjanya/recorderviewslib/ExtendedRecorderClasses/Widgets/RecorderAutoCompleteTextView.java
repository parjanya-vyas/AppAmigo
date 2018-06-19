package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.TextChangedEvent;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

public class RecorderAutoCompleteTextView extends AppCompatAutoCompleteTextView implements ParentRecorderView {
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
                ListView parentListView = Utils.getParentListView(RecorderAutoCompleteTextView.this);
                RecyclerView parentRecyclerView = Utils.getParentRecyclerView(RecorderAutoCompleteTextView.this);
                View listItemParentView = Utils.getParentListItemView(RecorderAutoCompleteTextView.this);
                View recyclerItemView = Utils.getParentRecyclerItemView(RecorderAutoCompleteTextView.this);
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
    public RecorderAutoCompleteTextView(Context context) {
        super(context);
        init();
    }
    public RecorderAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }
    public RecorderAutoCompleteTextView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init();
    }
}
