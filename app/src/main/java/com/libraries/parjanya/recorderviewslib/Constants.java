package com.libraries.parjanya.recorderviewslib;

/**
 * Created by bhagyesh on 4/3/18.
 */

public class Constants {
    public static final String CLICK_EVENT_TAG_NAME = "clickEvent";
    public static final String LONG_PRESS_EVENT_TAG_NAME = "longPressEvent";
    public static final String KEY_PRESS_EVENT_TAG_NAME = "keyPressEvent";
    public static final String TEXT_CHANGED_EVENT_TAG_NAME = "textChangedEvent";
    public static final String VIEW_PAGER_ITEM_SELECTED_EVENT_TAG_NAME = "viewPagerItemSelectedEvent";
    public static final String SPINNER_ITEM_SELECTED_EVENT_TAG_NAME = "spinnerItemSelectedEvent";
    public static final String MENU_ITEM_SELECTED_EVENT_TAG_NAME = "menuItemSelectedEvent";

    public static final String EVENT_TYPE_ATTRIBUTE_NAME = "eventType";
    public static final String VIEW_ID_ATTRIBUTE_NAME = "viewId";
    public static final String KEY_CODE_ATTRIBUTE_NAME = "keyCode";
    public static final String CHANGED_TEXT_ATTRIBUTE_NAME = "changedText";
    public static final String ITEM_ID_ATTRIBUTE_NAME = "itemId";
    public static final String PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME = "parentListViewId";
    public static final String LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME = "listViewItemId";

    public static final String BUTTON_EVENT_TYPE_ATTRIBUTE = "recorderButton";
    public static final String CHECKBOX_EVENT_TYPE_ATTRIBUTE = "recorderCheckbox";
    public static final String SWITCH_EVENT_TYPE_ATTRIBUTE = "recorderSwitch";
    public static final String EDIT_TEXT_EVENT_TYPE_ATTRIBUTE = "recorderEditText";
    public static final String TEXT_VIEW_EVENT_TYPE_ATTRIBUTE = "recorderTextView";
    public static final String VIEW_PAGER_EVENT_TYPE_ATTRIBUTE = "recorderViewPager";
    public static final String SPINNER_EVENT_TYPE_ATTRIBUTE = "recorderSpinner";
    public static final String KEY_EVENT_TYPE_ATTRIBUTE = "recorderKey";
    public static final String MENU_EVENT_TYPE_ATTRIBUTE = "recorderMenu";

    public static final String ACTION_START_SERVER = "startServer";
    public static final String ACTION_CONNECT_CLIENT = "connectClient";
    public static final String ACTION_DISCONNECT_CLIENT = "disconnectClient";
    public static final String ACTION_START_RECORDING = "startRecording";
    public static final String ACTION_STOP_RECORDING = "stopRecording";
    public static final String ACTION_START_PLAYING = "startPlaying";
    public static final String ACTION_CHANGE_MODE = "changeMode";

    public static final int NETWORK_NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_CHANNEL = "myChannel";

    public static final int RECORDING_MODE_ONLINE = 0;
    public static final int RECORDING_MODE_OFFLINE = 1;

    public static final String FILE_NAME = "Data.xml";
    public static final String DISCONNECT_MESSAGE = "disconnect";
    public static final long PAUSE_TIME = 2000;
    public static final int SERVER_PORT = 7777;

    public static final int NO_LIST_VIEW = -1;
    public static final String NO_ID = "-1";
}
