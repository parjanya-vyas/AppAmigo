package com.libraries.parjanya.recorderviewslib.ExtendedRecorderClasses;

import android.view.View;

public abstract class PlayerRunnable implements Runnable {
    public View targetView;
    public void setTargetView(View targetView) {
        this.targetView = targetView;
    }
}
