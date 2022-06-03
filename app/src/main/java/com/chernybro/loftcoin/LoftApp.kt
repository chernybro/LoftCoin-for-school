package com.chernybro.loftcoin

import android.app.Application
import android.os.StrictMode
import androidx.viewbinding.BuildConfig

class LoftApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }
    }
}