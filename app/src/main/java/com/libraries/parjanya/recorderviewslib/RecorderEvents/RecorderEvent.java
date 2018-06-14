package com.libraries.parjanya.recorderviewslib.RecorderEvents;

import android.app.Activity;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 12/2/18.
 */

public abstract class RecorderEvent {
    public String viewId;
    public String eventType;
    public XMLCreator xmlCreator;
    public String parentListViewId;
    public int listViewItemId;

    public RecorderEvent(String viewId, String eventType, String parentListViewId, int listViewItemId, XMLCreator xmlCreator) {
        this.viewId = viewId;
        this.eventType = eventType;
        this.parentListViewId = parentListViewId;
        this.listViewItemId = listViewItemId;
        this.xmlCreator = xmlCreator;
    }

    abstract public void playEvent(Activity recorderActivity);
    abstract public void saveEvent();
}