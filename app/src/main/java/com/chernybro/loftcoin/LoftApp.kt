package com.chernybro.loftcoin

import android.app.Application
import android.os.StrictMode
import com.chernybro.loftcoin.utils.logging.DebugTree
import timber.log.Timber

class LoftApp : Application() {

    private lateinit var component: BaseComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(DebugTree())
        }
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    fun getComponent(): BaseComponent {
        return component
    }

}