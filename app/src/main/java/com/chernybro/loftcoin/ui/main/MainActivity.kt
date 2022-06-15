package com.chernybro.loftcoin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        val navController = findNavController(this, R.id.main_host)
        setupWithNavController(binding.bottomNav, navController)
        setupWithNavController(
            binding.toolbar, navController, AppBarConfiguration.Builder(binding.bottomNav.menu)
                .build()
        )
    }
}