package com.paularagones.moode.Application;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Paul.Aragones on 2/29/2016.
 */
public class MooderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }

}
