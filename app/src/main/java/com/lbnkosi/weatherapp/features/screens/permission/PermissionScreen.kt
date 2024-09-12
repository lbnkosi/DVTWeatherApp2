package com.lbnkosi.weatherapp.features.screens.permission

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*

@Composable
fun RequestLocationScreen(
    onRequestPermission: () -> Unit,
    permissionGranted: Boolean,
    onContinueToApp: () -> Unit
) {
    LocalContext.current
    var buttonText by remember { mutableStateOf("Grant Location Permission") }

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("location_animation.json"))
    val progress by animateLottieCompositionAsState(composition)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "We need your location",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom= 16.dp)
        )

        Text(
            text = "We need your location to get the weather information for your current area.",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom= 24.dp)
        )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom= 32.dp)
        )

        Button(
            onClick = {
                if (!permissionGranted) {
                    onRequestPermission()
                } else {
                    onContinueToApp()
                }
            },
            enabled = permissionGranted || !permissionGranted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (permissionGranted) "Continue to App" else buttonText)
        }
    }
}