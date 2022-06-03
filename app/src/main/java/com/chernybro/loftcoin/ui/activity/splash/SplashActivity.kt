package com.chernybro.loftcoin.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.ui.activity.main.MainActivity
import com.chernybro.loftcoin.ui.activity.welcome.WelcomeActivity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        compositeDisposable.add(Completable.timer(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (prefs.getBoolean(WelcomeActivity.KEY_SHOW_WELCOME, true)) {
                        startActivity(Intent(this, WelcomeActivity::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                },
                {
                    Log.d("Splash", "onCreate: $it")
                }
            ))
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }
}