package com.lbnkosi.weatherapp.features.screens.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.lbnkosi.weatherapp.features.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        permissionGranted = isGranted
    }

    private var permissionGranted by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RequestLocationScreen(
                onRequestPermission = {
                    // Request permission if not granted
                    when (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )) {
                        PackageManager.PERMISSION_GRANTED -> {
                            // Permission already granted
                            permissionGranted = true
                        }
                        else -> {
                            // Request permission
                            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                },
                permissionGranted = permissionGranted,
                onContinueToApp = {
                    // Start MainActivity when permission is granted and the user clicks "Continue to App"
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Optionally, close the permission screen
                }
            )
        }
    }
}