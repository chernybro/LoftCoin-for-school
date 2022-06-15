package com.chernybro.loftcoin.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.chernybro.loftcoin.databinding.ActivityWelcomeBinding
import com.chernybro.loftcoin.ui.activity.welcome.WelcomeAdapter
import com.chernybro.loftcoin.ui.main.MainActivity
import com.chernybro.loftcoin.ui.widget.CircleIndicator

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var helper: SnapHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.recycler.addItemDecoration(CircleIndicator(this))
        binding.recycler.adapter = WelcomeAdapter()
        binding.recycler.setHasFixedSize(true)
        helper = PagerSnapHelper()
        (helper as PagerSnapHelper).attachToRecyclerView(binding.recycler)
        binding.btnStart.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putBoolean(KEY_SHOW_WELCOME, false)
                .apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        helper.attachToRecyclerView(null)
        binding.recycler.adapter = null
        super.onDestroy()
    }

    companion object {
        const val KEY_SHOW_WELCOME = "show_welcome"
    }
}