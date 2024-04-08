package com.qualtrics.sample_views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qualtrics.sample_common.examples.evaluateInterceptAndDisplay
import com.qualtrics.sample_common.examples.registerViewVisitWithEmbeddedData
import com.qualtrics.sample_views.databinding.ActivityBottomNavExampleBinding
import com.qualtrics.sample_views.helpers.Constants

class BottomNavExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavExampleBinding
    private lateinit var navController: NavController

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        Log.d(Constants.LOG_TAG, "View changed to $destination")
        registerViewVisitWithEmbeddedData(
            activity = this@BottomNavExampleActivity,
            viewName = destination.label.toString(),
            embeddedData = hashMapOf("productName" to "Great Brand")
        )
        evaluateInterceptAndDisplay(
            this@BottomNavExampleActivity,
            Constants.QUALTRICS_INTERCEPT_ID,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(listener)
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(listener)
        super.onDestroy()
    }
}