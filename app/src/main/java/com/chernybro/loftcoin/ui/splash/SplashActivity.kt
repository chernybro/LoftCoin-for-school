package com.chernybro.loftcoin.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.ui.main.MainActivity
import com.chernybro.loftcoin.ui.welcome.WelcomeActivity
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        compositeDisposable.add(Completable.timer(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                if (prefs.getBoolean(WelcomeActivity.KEY_SHOW_WELCOME, true)) {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            })
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }
}