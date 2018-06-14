package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.KeyPressEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.MenuItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderReceiver;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;
import com.libraries.parjanya.recorderviewslib.XMLHandler.XMLCreator;

/**
 * Created by parjanya on 4/3/18.
 */

public abstract class RecorderAppCompatActivity extends AppCompatActivity {
    RecorderReceiver recorderReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recorderReceiver = new RecorderReceiver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.initializeNotifications(this, true);
        registerReceiver(recorderReceiver, Utils.getIntentFilterForAllActions());
        Utils.setCurrentActivity(this);
        if (Utils.isRecordingEnabled())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setCurrentActivity(this);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(recorderReceiver);
        Utils.removeNotifications(this);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Utils.isRecordingEnabled()) {
            KeyPressEvent keyPressEvent = new KeyPressEvent(keyCode, Constants.KEY_EVENT_TYPE_ATTRIBUTE, new XMLCreator(this));
            keyPressEvent.saveEvent();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Utils.setCurrentMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Utils.isRecordingEnabled()) {
            MenuItemSelectedEvent menuItemSelectedEvent = new MenuItemSelectedEvent(Constants.MENU_EVENT_TYPE_ATTRIBUTE,
                    new XMLCreator(this), Utils.getMenuItemIdStringFromMenuItem(item, this));
            menuItemSelectedEvent.saveEvent();
        }
        return super.onOptionsItemSelected(item);
    }
}