package com.chernybro.loftcoin.ui.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.chernybro.loftcoin.BaseComponent
import com.chernybro.loftcoin.LoftApp
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var component: MainComponent

    @JvmField
    @Inject
    var fragmentFactory: FragmentFactory? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        val baseComponent: BaseComponent = (newBase.applicationContext as LoftApp).getComponent()
        component = DaggerMainComponent.builder().baseComponent(baseComponent).build()
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory!!
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