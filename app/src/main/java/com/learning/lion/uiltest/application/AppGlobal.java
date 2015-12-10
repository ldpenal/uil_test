package com.learning.lion.uiltest.application;

import android.app.Application;
import android.content.Context;

import com.learning.lion.uiltest.processor.ImageWorkerBridge;


/**
 * Created by lion on 12/7/15.
 */
public class AppGlobal extends Application {

    private ImageWorkerBridge imageProcessor;
    private static AppGlobal instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        imageProcessor = new ImageWorkerBridge(this);
    }

    public Context getContext() {
        return this;
    }

    public static AppGlobal instance() {
        return instance;
    }

    public ImageWorkerBridge getImageProcessor() {
        return this.imageProcessor;
    }
}
