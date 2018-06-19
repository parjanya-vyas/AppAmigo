package com.libraries.parjanya.recorderviewslib.Utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.PlayerRunnable;
import com.libraries.parjanya.recorderviewslib.R;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ClickEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.KeyPressEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.LongPressEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.MenuItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.RecorderEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.SpinnerItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.TextChangedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ViewPagerItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

import java.lang.ref.WeakReference;

/**
 * Created by parjanya on 12/2/18.
 */

public class Utils {
    private static boolean recordingEnabled = false;
    private static int recordingMode = Constants.RECORDING_MODE_ONLINE;

    private static boolean rootTagStarted = false;
    private static boolean rootTagEnded = false;

    private static int notificationDisplayCount = 0;

    private static int currentViewPagerId;

    private static WeakReference<Activity> currentActivity;

    public static Activity getCurrentActivity() {
        return currentActivity.get();
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = new WeakReference<>(activity);
    }

    public static int getCurrentViewPagerId() {
        return currentViewPagerId;
    }

    public static void setCurrentViewPagerId(int viewPagerId) {
        currentViewPagerId = viewPagerId;
    }

    public static void setRecordingState(boolean recordingState) {
        recordingEnabled = recordingState;
    }

    public static boolean isRecordingEnabled() {
        return recordingEnabled;
    }

    public static int getRecordingMode() {
        return recordingMode;
    }

    public static void toggleRecordingMode() {
        recordingMode = 1 - recordingMode;
    }

    public static boolean isRootTagStarted() {
        return rootTagStarted;
    }

    public static boolean isRootTagEnded() {
        return rootTagEnded;
    }

    public static void setRootTagStarted(boolean rootTagStarted) {
        Utils.rootTagStarted = rootTagStarted;
    }

    public static void setRootTagEnded(boolean rootTagEnded) {
        Utils.rootTagEnded = rootTagEnded;
    }

    public static String getViewIdStringFromView(View view) {
        if (view == null)
            return Constants.NO_ID;
        if (view.getId() == (int)0xffffffff)
            return Constants.NO_ID;
        else
            return view.getResources().getResourceName(view.getId());
    }

    public static int getViewIdIntFromString(String idString, Context context) {
        return context.getResources().getIdentifier(idString, "id", context.getPackageName());
    }

    public static String getMenuItemIdStringFromMenuItem(MenuItem menuItem, Context context) {
        return context.getResources().getResourceName(menuItem.getItemId());
    }

    private static ListView getParentListViewRec(ViewParent childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof ListView)
            return (ListView)parent;
        return getParentListViewRec(parent);
    }

    public static ListView getParentListView(View childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof ListView)
            return (ListView)parent;
        return getParentListViewRec(parent);
    }

    private static RecyclerView getParentRecyclerViewRec(ViewParent childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof RecyclerView)
            return (RecyclerView)parent;
        return getParentRecyclerViewRec(parent);
    }

    public static RecyclerView getParentRecyclerView(View childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof RecyclerView)
            return (RecyclerView)parent;
        return getParentRecyclerViewRec(parent);
    }

    private static View getParentListItemViewRec(ViewParent childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof ListView && childView instanceof View)
            return (View) childView;
        return getParentListItemViewRec(parent);
    }

    public static View getParentListItemView(View childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof ListView)
            return childView;
        return getParentListItemViewRec(parent);
    }

    private static View getParentRecyclerItemViewRec(ViewParent childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof RecyclerView && childView instanceof View)
            return (View) childView;
        return getParentRecyclerItemViewRec(parent);
    }

    public static View getParentRecyclerItemView(View childView) {
        ViewParent parent = childView.getParent();
        if (parent == null)
            return null;
        if (parent instanceof RecyclerView)
            return childView;
        return getParentRecyclerItemViewRec(parent);
    }

    public static void getViewFromParentListViewAndRun(final String curViewId, String parentListViewId, final View rootView,
                                                       final int listViewItemId, final PlayerRunnable playerRunnable) {
        View parent = rootView.findViewById(Utils.getViewIdIntFromString(parentListViewId, rootView.getContext()));
        ListView listView = parent instanceof ListView ? (ListView)parent : null;
        RecyclerView recyclerView = parent instanceof RecyclerView ? (RecyclerView)parent : null;
        if (listView != null) {
            listView.smoothScrollToPosition(listViewItemId);
            View targetView, listItem = Utils.getViewFromListViewByPosition(listViewItemId, listView);
            if (listItem.getId() == Utils.getViewIdIntFromString(curViewId, rootView.getContext()))
                targetView = listItem;
            else
                targetView = listItem.findViewById(Utils.getViewIdIntFromString(curViewId, rootView.getContext()));

            playerRunnable.setTargetView(targetView);
            playerRunnable.run();
        } else if (recyclerView != null){
            View targetView, listItem = recyclerView.getLayoutManager().findViewByPosition(listViewItemId);
            if (listItem != null) {
                if (listItem.getId() == Utils.getViewIdIntFromString(curViewId, rootView.getContext()))
                    targetView = listItem;
                else
                    targetView = listItem.findViewById(Utils.getViewIdIntFromString(curViewId, rootView.getContext()));
                playerRunnable.setTargetView(targetView);
                playerRunnable.run();
            } else {
                recyclerView.smoothScrollToPosition(listViewItemId);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        View targetView, listItem;
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            listItem = recyclerView.getLayoutManager().findViewByPosition(listViewItemId);
                            if (listItem.getId() == Utils.getViewIdIntFromString(curViewId, rootView.getContext()))
                                targetView = listItem;
                            else
                                targetView = listItem.findViewById(Utils.getViewIdIntFromString(curViewId, rootView.getContext()));
                            playerRunnable.setTargetView(targetView);
                            playerRunnable.run();
                            recyclerView.removeOnScrollListener(this);
                        }
                    }
                });
            }
        }
    }

    private static View getViewFromListViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public static void initializeNotifications(Context context, boolean fromActivity) {
        RemoteViews notificationViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        Intent changeModeIntent = new Intent(Constants.ACTION_CHANGE_MODE);
        PendingIntent changeModePendingIntent = PendingIntent.getBroadcast(context, 0, changeModeIntent, 0);
        notificationViews.setOnClickPendingIntent(R.id.notification_switch_to_online_button, changeModePendingIntent);
        notificationViews.setOnClickPendingIntent(R.id.notification_switch_to_offline_button, changeModePendingIntent);

        switch (recordingMode) {
            case Constants.RECORDING_MODE_ONLINE:
                notificationViews.setViewVisibility(R.id.notification_online_container, View.VISIBLE);
                notificationViews.setViewVisibility(R.id.notification_offline_container, View.GONE);

                if (recordingEnabled) {
                    notificationViews.setViewVisibility(R.id.notification_client_stop_button, View.VISIBLE);
                    notificationViews.setViewVisibility(R.id.notification_connect_button, View.GONE);
                } else {
                    notificationViews.setViewVisibility(R.id.notification_connect_button, View.VISIBLE);
                    notificationViews.setViewVisibility(R.id.notification_client_stop_button, View.GONE);
                }

                Intent startServerIntent = new Intent(Constants.ACTION_START_SERVER);
                Intent connectClientIntent = new Intent(Constants.ACTION_CONNECT_CLIENT);
                Intent disconnectClientIntent = new Intent(Constants.ACTION_DISCONNECT_CLIENT);

                PendingIntent requestPendingIntent = PendingIntent.getBroadcast(context, 0, startServerIntent, 0);
                PendingIntent connectPendingIntent = PendingIntent.getBroadcast(context, 0, connectClientIntent, 0);
                PendingIntent disconnectPendingIntent = PendingIntent.getBroadcast(context, 0, disconnectClientIntent, 0);

                notificationViews.setOnClickPendingIntent(R.id.notification_request_button, requestPendingIntent);
                notificationViews.setOnClickPendingIntent(R.id.notification_connect_button, connectPendingIntent);
                notificationViews.setOnClickPendingIntent(R.id.notification_client_stop_button, disconnectPendingIntent);
                break;

            case Constants.RECORDING_MODE_OFFLINE:
                notificationViews.setViewVisibility(R.id.notification_offline_container, View.VISIBLE);
                notificationViews.setViewVisibility(R.id.notification_online_container, View.GONE);

                if (recordingEnabled) {
                    notificationViews.setViewVisibility(R.id.notification_stop_button, View.VISIBLE);
                    notificationViews.setViewVisibility(R.id.notification_record_button, View.GONE);
                } else {
                    notificationViews.setViewVisibility(R.id.notification_record_button, View.VISIBLE);
                    notificationViews.setViewVisibility(R.id.notification_stop_button, View.GONE);
                }

                Intent startRecordingIntent = new Intent(Constants.ACTION_START_RECORDING);
                Intent stopRecordingIntent = new Intent(Constants.ACTION_STOP_RECORDING);
                Intent startPlayingIntent = new Intent(Constants.ACTION_START_PLAYING);

                PendingIntent recordPendingIntent = PendingIntent.getBroadcast(context, 0, startRecordingIntent, 0);
                PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopRecordingIntent, 0);
                PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 0, startPlayingIntent, 0);

                notificationViews.setOnClickPendingIntent(R.id.notification_record_button, recordPendingIntent);
                notificationViews.setOnClickPendingIntent(R.id.notification_stop_button, stopPendingIntent);
                notificationViews.setOnClickPendingIntent(R.id.notification_play_button, playPendingIntent);
        }

        Notification notification = new NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL)
                .setContentTitle("Awesome Notification")
                .setContentText("Isn't this awesome?")
                .setSmallIcon(R.drawable.notification_icon)
                .setContent(notificationViews)
                .setOngoing(true)
                .build();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (fromActivity)
            notificationDisplayCount++;
        notificationManager.notify(Constants.NETWORK_NOTIFICATION_ID, notification);
    }

    public static void removeNotifications(Context context) {
        if (--notificationDisplayCount == 0) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(Constants.NETWORK_NOTIFICATION_ID);
        }
    }

    public static IntentFilter getIntentFilterForAllActions() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_START_SERVER);
        intentFilter.addAction(Constants.ACTION_CONNECT_CLIENT);
        intentFilter.addAction(Constants.ACTION_DISCONNECT_CLIENT);
        intentFilter.addAction(Constants.ACTION_START_RECORDING);
        intentFilter.addAction(Constants.ACTION_STOP_RECORDING);
        intentFilter.addAction(Constants.ACTION_START_PLAYING);
        intentFilter.addAction(Constants.ACTION_CHANGE_MODE);

        return intentFilter;
    }

    public static String serializeEvent(RecorderEvent recorderEvent) {
        String finalOutput="";

        if (recorderEvent instanceof ClickEvent) {
            ClickEvent clickEvent = (ClickEvent)recorderEvent;
            finalOutput += Constants.CLICK_EVENT_TAG_NAME + " "
                    + clickEvent.eventType + " "
                    + clickEvent.viewId + " "
                    + clickEvent.parentListViewId + " "
                    + clickEvent.listViewItemId;
        } else if (recorderEvent instanceof KeyPressEvent) {
            KeyPressEvent keyPressEvent = (KeyPressEvent)recorderEvent;
            finalOutput += Constants.KEY_PRESS_EVENT_TAG_NAME + " "
                    + keyPressEvent.eventType + " "
                    + keyPressEvent.keyCode;
        } else if (recorderEvent instanceof LongPressEvent) {
            LongPressEvent longPressEvent = (LongPressEvent)recorderEvent;
            finalOutput += Constants.LONG_PRESS_EVENT_TAG_NAME + " " +
                    longPressEvent.eventType + " "
                    + longPressEvent.viewId + " "
                    + longPressEvent.parentListViewId + " "
                    + longPressEvent.listViewItemId;
        } else if (recorderEvent instanceof SpinnerItemSelectedEvent) {
            SpinnerItemSelectedEvent spinnerItemSelectedEvent = (SpinnerItemSelectedEvent)recorderEvent;
            finalOutput += Constants.SPINNER_ITEM_SELECTED_EVENT_TAG_NAME + " "
                    + spinnerItemSelectedEvent.eventType + " "
                    + spinnerItemSelectedEvent.viewId + " "
                    + spinnerItemSelectedEvent.itemId + " "
                    + spinnerItemSelectedEvent.parentListViewId + " "
                    + spinnerItemSelectedEvent.listViewItemId;;
        } else if (recorderEvent instanceof TextChangedEvent) {
            TextChangedEvent textChangedEvent = (TextChangedEvent)recorderEvent;
            finalOutput += Constants.TEXT_CHANGED_EVENT_TAG_NAME + " "
                    + textChangedEvent.eventType + " "
                    + textChangedEvent.viewId + " "
                    + textChangedEvent.changedText + " "
                    + textChangedEvent.parentListViewId + " "
                    + textChangedEvent.listViewItemId;
        } else if (recorderEvent instanceof ViewPagerItemSelectedEvent) {
            ViewPagerItemSelectedEvent viewPagerItemSelectedEvent = (ViewPagerItemSelectedEvent)recorderEvent;
            finalOutput += Constants.VIEW_PAGER_ITEM_SELECTED_EVENT_TAG_NAME + " "
                    + viewPagerItemSelectedEvent.eventType + " "
                    + viewPagerItemSelectedEvent.viewId + " "
                    + viewPagerItemSelectedEvent.itemId;
        } else if (recorderEvent instanceof MenuItemSelectedEvent) {
            MenuItemSelectedEvent menuItemSelectedEvent = (MenuItemSelectedEvent) recorderEvent;
            finalOutput += Constants.MENU_ITEM_SELECTED_EVENT_TAG_NAME + " "
                    + menuItemSelectedEvent.eventType + " "
                    + menuItemSelectedEvent.itemId;
        }

        return finalOutput;
    }

    public static RecorderEvent deserializeEvent(String eventString, Context context) {
        String[] eventAttributes = eventString.split(" ");
        switch (eventAttributes[0]) {
            case Constants.CLICK_EVENT_TAG_NAME:
                return new ClickEvent(eventAttributes[2], eventAttributes[1],
                        eventAttributes[3], Integer.parseInt(eventAttributes[4]), new XMLCreator(context));
            case Constants.KEY_PRESS_EVENT_TAG_NAME:
                return new KeyPressEvent(Integer.parseInt(eventAttributes[2]), eventAttributes[1], new XMLCreator(context));
            case Constants.LONG_PRESS_EVENT_TAG_NAME:
                return new LongPressEvent(eventAttributes[2], eventAttributes[1],
                        eventAttributes[3], Integer.parseInt(eventAttributes[4]), new XMLCreator(context));
            case Constants.SPINNER_ITEM_SELECTED_EVENT_TAG_NAME:
                return new SpinnerItemSelectedEvent(eventAttributes[2], eventAttributes[1],
                        eventAttributes[4], Integer.parseInt(eventAttributes[5]), new XMLCreator(context), Integer.parseInt(eventAttributes[3]));
            case Constants.TEXT_CHANGED_EVENT_TAG_NAME:
                if (eventAttributes.length == 6)
                    return new TextChangedEvent(eventAttributes[3], eventAttributes[2],
                            eventAttributes[4], Integer.parseInt(eventAttributes[5]),
                            eventAttributes[1], new XMLCreator(context));
                else
                    return new TextChangedEvent("", eventAttributes[2],
                            eventAttributes[3], Integer.parseInt(eventAttributes[4]),
                            eventAttributes[1], new XMLCreator(context));
            case Constants.VIEW_PAGER_ITEM_SELECTED_EVENT_TAG_NAME:
                return new ViewPagerItemSelectedEvent(eventAttributes[2], eventAttributes[1], new XMLCreator(context), Integer.parseInt(eventAttributes[3]));
            case Constants.MENU_ITEM_SELECTED_EVENT_TAG_NAME:
                return new MenuItemSelectedEvent(eventAttributes[1],new XMLCreator(context), eventAttributes[2]);
        }

        return null;
    }
}
