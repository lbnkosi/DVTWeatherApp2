package com.lbnkosi.weatherapp.features.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.lbnkosi.weatherapp.features.screens.home.navigation.homeNavigationRoute
import com.lbnkosi.weatherapp.features.utils.Utility.getAnimateFloat
import kotlinx.coroutines.delay

@Composable
fun WeatherAppFloatingActionBar(
    navController: NavController,
) {
    var isClick by remember { mutableStateOf(false) }

    LaunchedEffect(isClick) {
        if (isClick) {
            delay(800)
            isClick = false
        }
    }

    FloatingActionButton(
        onClick = {
            isClick = true
            navController.navigate(homeNavigationRoute) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        },
        contentColor = Color.White,
        backgroundColor = Color.White,
        shape = CircleShape,
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(if (isClick) getAnimateFloat().value.dp else 24.dp)
        )
    }
}
