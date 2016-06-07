package com.siwei.app;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by siwei.zhao on 2016/6/2.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("","Fresco.initialize");
        Fresco.initialize(getApplicationContext());
    }
}
