package com.lbnkosi.weatherapp.features.screens.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.lbnkosi.weatherapp.features.screens.main.MainActivity
import com.lbnkosi.weatherapp.features.screens.permission.PermissionActivity
import com.lbnkosi.weatherapp.features.utils.Utility.launchActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        lifecycleScope.launchWhenCreated {
            viewModel.uiEvent.collect {
                when (it) {
                    is SplashViewEvent.DirectToDashBoard -> {
                        startPermissionsActivity()
                        finish()
                    }
                }
            }
        }
    }


    private fun startPermissionsActivity() {
        when (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PERMISSION_GRANTED -> {
                launchActivity(packageName = packageName, className = MainActivity::class.java.name)
            }
            else -> {
                launchActivity(packageName = packageName, className = PermissionActivity::class.java.name)
            }
        }
    }
}