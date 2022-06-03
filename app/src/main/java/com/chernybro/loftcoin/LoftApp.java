package com.chernybro.loftcoin;

import android.app.Application;
import android.os.StrictMode;

import androidx.viewbinding.BuildConfig;

public class LoftApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
    }
}
