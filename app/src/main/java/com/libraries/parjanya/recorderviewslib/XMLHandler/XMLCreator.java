package com.libraries.parjanya.recorderviewslib.XMLHandler;

import android.content.Context;
import android.util.Log;

import com.libraries.parjanya.recorderviewslib.Constants;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ClickEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.KeyPressEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.LongPressEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.MenuItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.SpinnerItemSelectedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.TextChangedEvent;
import com.libraries.parjanya.recorderviewslib.RecorderEvents.ViewPagerItemSelectedEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by bhagyesh on 3/3/18.
 */

public class XMLCreator {
    PrintWriter out;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    File file;
    Context context;

    public XMLCreator(Context context) {
        this.context = context;
    }

    public void appendStartRootElement(){
        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<recordedEvents>\n");
            out.close();

        } catch (Exception e) {
            Log.d("Parjanya",e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendEndRootElement(){
        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("\n</recordedEvents>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendClickEvent(ClickEvent event) {

        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<" + Constants.CLICK_EVENT_TAG_NAME
                    + "\n" + Constants.EVENT_TYPE_ATTRIBUTE_NAME + " = '" + event.eventType
                    + "'\n" + Constants.VIEW_ID_ATTRIBUTE_NAME + " = '" + event.viewId
                    + "'\n" + Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME + " = '" + event.parentListViewId
                    + "'\n" + Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME + " = '" + event.listViewItemId + "'/>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendLongPressEvent(LongPressEvent event) {

        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<" + Constants.LONG_PRESS_EVENT_TAG_NAME
                    + "\n" + Constants.EVENT_TYPE_ATTRIBUTE_NAME + " = '" + event.eventType
                    + "'\n" + Constants.VIEW_ID_ATTRIBUTE_NAME + " = '" + event.viewId
                    + "'\n" + Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME + " = '" + event.parentListViewId
                    + "'\n" + Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME + " = '" + event.listViewItemId + "'/>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendKeyPressEvent(KeyPressEvent event) {

        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<" + Constants.KEY_PRESS_EVENT_TAG_NAME
                    + "\n" + Constants.KEY_CODE_ATTRIBUTE_NAME + " = '" + event.keyCode + "'/>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendViewPagerItemSelectedEvent(ViewPagerItemSelectedEvent event) {

        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<" + Constants.VIEW_PAGER_ITEM_SELECTED_EVENT_TAG_NAME
                    + "\n" + Constants.EVENT_TYPE_ATTRIBUTE_NAME + " = '" + event.eventType
                    + "'\n" + Constants.VIEW_ID_ATTRIBUTE_NAME + " = '" + event.viewId
                    + "'\n" + Constants.ITEM_ID_ATTRIBUTE_NAME + " = '" + event.itemId + "'/>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendMenuItemSelectedEvent(MenuItemSelectedEvent event) {

        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<" + Constants.MENU_ITEM_SELECTED_EVENT_TAG_NAME
                    + "\n" + Constants.EVENT_TYPE_ATTRIBUTE_NAME + " = '" + event.eventType
                    + "'\n" + Constants.ITEM_ID_ATTRIBUTE_NAME + " = '" + event.itemId + "'/>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendSpinnerItemSelectedEvent(SpinnerItemSelectedEvent event) {

        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<" + Constants.SPINNER_ITEM_SELECTED_EVENT_TAG_NAME
                    + "\n" + Constants.EVENT_TYPE_ATTRIBUTE_NAME + " = '" + event.eventType
                    + "'\n" + Constants.VIEW_ID_ATTRIBUTE_NAME + " = '" + event.viewId
                    + "'\n" + Constants.ITEM_ID_ATTRIBUTE_NAME + " = '" + event.itemId
                    + "'\n" + Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME + " = '" + event.parentListViewId
                    + "'\n" + Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME + " = '" + event.listViewItemId + "'/>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendTextChangedEvent(TextChangedEvent event) {

        String fileName = Constants.FILE_NAME;

        file = new File(context.getExternalFilesDir(null), fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            out.println("<" + Constants.TEXT_CHANGED_EVENT_TAG_NAME
                    + "\n" + Constants.EVENT_TYPE_ATTRIBUTE_NAME + " = '" + event.eventType
                    + "'\n" + Constants.VIEW_ID_ATTRIBUTE_NAME + " = '" + event.viewId
                    + "'\n" + Constants.CHANGED_TEXT_ATTRIBUTE_NAME + " = '" + event.changedText
                    + "'\n" + Constants.PARENT_LIST_VIEW_ID_ATTRIBUTE_NAME + " = '" + event.parentListViewId
                    + "'\n" + Constants.LIST_VIEW_ITEM_ID_ATTRIBUTE_NAME + " = '" + event.listViewItemId + "'/>");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
