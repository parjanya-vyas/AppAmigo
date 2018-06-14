package com.libraries.parjanya.recorderviewslib.XMLHandler;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ClickEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.KeyPressEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.LongPressEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.MenuItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.RecorderEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.SpinnerItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.TextChangedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ViewPagerItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.Utils.Utils;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by bhagyesh on 3/3/18.
 */

public class XMLParser {
    Activity recorderActivity;
    File inputFile;
    SAXParserFactory factory;
    SAXParser saxParser;
    XMLCreator xmlCreator;

    public XMLParser(Activity recorderActivity) {
        this.recorderActivity = recorderActivity;
        xmlCreator = new XMLCreator(recorderActivity);

        try{
            inputFile = new File(recorderActivity.getExternalFilesDir(null), Constants.FILE_NAME);
            Reader reader = new InputStreamReader(new FileInputStream(inputFile));
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(reader);
            factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();

            saxParser.parse(inputSource, userhandler);

        } catch(Exception e){
            Log.d("Parser",e.getMessage());
            e.printStackTrace();
        }
    }

    class UserHandler extends DefaultHandler {
        ArrayList <RecorderEvent> parsedEventsList;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();

            parsedEventsList = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            if (qName.equalsIgnoreCase(Constants.CLICK_EVENT_TAG_NAME)) {
                String eventType = attributes.getValue(Constants.EVENT_TYPE_ATTRIBUTE_NAME);
                String viewId = attributes.getValue(Constants.VIEW_ID_ATTRIBUTE_NAME);
                String parentListViewId = attributes.getValue(Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME);
                int listViewItemId = Integer.parseInt(attributes.getValue(Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME));
                ClickEvent clickEvent = new ClickEvent(viewId,eventType,parentListViewId,listViewItemId,xmlCreator);
                parsedEventsList.add(clickEvent);
            }
            else if (qName.equalsIgnoreCase(Constants.LONG_PRESS_EVENT_TAG_NAME)){
                String eventType = attributes.getValue(Constants.EVENT_TYPE_ATTRIBUTE_NAME);
                String viewId = attributes.getValue(Constants.VIEW_ID_ATTRIBUTE_NAME);
                String parentListViewId = attributes.getValue(Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME);
                int listViewItemId = Integer.parseInt(attributes.getValue(Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME));
                LongPressEvent longPressEvent = new LongPressEvent(viewId,eventType,parentListViewId,listViewItemId,xmlCreator);
                parsedEventsList.add(longPressEvent);
            }
            else if (qName.equalsIgnoreCase(Constants.KEY_PRESS_EVENT_TAG_NAME)){
                String keyCode = attributes.getValue(Constants.KEY_CODE_ATTRIBUTE_NAME);
                KeyPressEvent keyPressEvent = new KeyPressEvent(Integer.parseInt(keyCode),Constants.KEY_EVENT_TYPE_ATTRIBUTE,xmlCreator);
                parsedEventsList.add(keyPressEvent);
            }
            else if (qName.equalsIgnoreCase(Constants.TEXT_CHANGED_EVENT_TAG_NAME)){
                String eventType = attributes.getValue(Constants.EVENT_TYPE_ATTRIBUTE_NAME);
                String viewId = attributes.getValue(Constants.VIEW_ID_ATTRIBUTE_NAME);
                String changedText = attributes.getValue(Constants.CHANGED_TEXT_ATTRIBUTE_NAME);
                String parentListViewId = attributes.getValue(Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME);
                int listViewItemId = Integer.parseInt(attributes.getValue(Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME));
                TextChangedEvent textChangedEvent = new TextChangedEvent(changedText,viewId,parentListViewId,listViewItemId,eventType,xmlCreator);
                parsedEventsList.add(textChangedEvent);
            }
            else if (qName.equalsIgnoreCase(Constants.VIEW_PAGER_ITEM_SELECTED_EVENT_TAG_NAME)){
                String eventType = attributes.getValue(Constants.EVENT_TYPE_ATTRIBUTE_NAME);
                String viewId = attributes.getValue(Constants.VIEW_ID_ATTRIBUTE_NAME);
                int itemId = Integer.parseInt(attributes.getValue(Constants.ITEM_ID_ATTRIBUTE_NAME));
                ViewPagerItemSelectedEvent viewPagerItemSelectedEvent = new ViewPagerItemSelectedEvent(viewId, eventType, xmlCreator, itemId);
                parsedEventsList.add(viewPagerItemSelectedEvent);
            }
            else if (qName.equalsIgnoreCase(Constants.SPINNER_ITEM_SELECTED_EVENT_TAG_NAME)){
                String eventType = attributes.getValue(Constants.EVENT_TYPE_ATTRIBUTE_NAME);
                String viewId = attributes.getValue(Constants.VIEW_ID_ATTRIBUTE_NAME);
                int itemId = Integer.parseInt(attributes.getValue(Constants.ITEM_ID_ATTRIBUTE_NAME));
                String parentListViewId = attributes.getValue(Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME);
                int listViewItemId = Integer.parseInt(attributes.getValue(Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME));
                SpinnerItemSelectedEvent spinnerItemSelectedEvent = new SpinnerItemSelectedEvent(viewId, eventType, parentListViewId, listViewItemId, xmlCreator, itemId);
                parsedEventsList.add(spinnerItemSelectedEvent);
            }
            else if (qName.equalsIgnoreCase(Constants.MENU_ITEM_SELECTED_EVENT_TAG_NAME)) {
                String eventType = attributes.getValue(Constants.EVENT_TYPE_ATTRIBUTE_NAME);
                String itemId = attributes.getValue(Constants.ITEM_ID_ATTRIBUTE_NAME);
                MenuItemSelectedEvent menuItemSelectedEvent = new MenuItemSelectedEvent(eventType, xmlCreator, itemId);
                parsedEventsList.add(menuItemSelectedEvent);
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            int i=1;
            for(RecorderEvent event: parsedEventsList){
                if(event instanceof ClickEvent){
                    final ClickEvent clickEvent = (ClickEvent)event;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recorderActivity = Utils.getCurrentActivity();
                            clickEvent.playEvent(recorderActivity);
                        }
                    }, i*Constants.PAUSE_TIME);
                }
                else if(event instanceof LongPressEvent){
                    final LongPressEvent longPressEvent = (LongPressEvent)event;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recorderActivity = Utils.getCurrentActivity();
                            longPressEvent.playEvent(recorderActivity);
                        }
                    }, i*Constants.PAUSE_TIME);
                }
                else if(event instanceof KeyPressEvent){
                    final KeyPressEvent keyPressEvent = (KeyPressEvent) event;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recorderActivity = Utils.getCurrentActivity();
                            keyPressEvent.playEvent(recorderActivity);
                        }
                    }, i*Constants.PAUSE_TIME);
                }
                else if(event instanceof TextChangedEvent){
                    final TextChangedEvent textChangedEvent = (TextChangedEvent) event;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recorderActivity = Utils.getCurrentActivity();
                            textChangedEvent.playEvent(recorderActivity);
                        }
                    }, i*Constants.PAUSE_TIME);
                }
                else if (event instanceof ViewPagerItemSelectedEvent){
                    final ViewPagerItemSelectedEvent viewPagerItemSelectedEvent = (ViewPagerItemSelectedEvent) event;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recorderActivity = Utils.getCurrentActivity();
                            viewPagerItemSelectedEvent.playEvent(recorderActivity);
                        }
                    }, i*Constants.PAUSE_TIME);
                }
                else if (event instanceof SpinnerItemSelectedEvent){
                    final SpinnerItemSelectedEvent spinnerItemSelectedEvent = (SpinnerItemSelectedEvent) event;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recorderActivity = Utils.getCurrentActivity();
                            spinnerItemSelectedEvent.playEvent(recorderActivity);
                        }
                    }, i*Constants.PAUSE_TIME);
                    i++;
                }
                else if (event instanceof MenuItemSelectedEvent){
                    final MenuItemSelectedEvent menuItemSelectedEvent = (MenuItemSelectedEvent) event;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recorderActivity = Utils.getCurrentActivity();
                            menuItemSelectedEvent.playEvent(recorderActivity);
                        }
                    }, i*Constants.PAUSE_TIME);
                    i++;
                }
                i++;
            }
        }
    }
}
