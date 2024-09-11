package com.lbnkosi.weatherapp.features.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lbnkosi.weatherapp.features.component.WeatherAppBottomAppBar
import com.lbnkosi.weatherapp.features.component.WeatherAppScaffold
import com.lbnkosi.weatherapp.features.screens.home.navigation.homeNavigationRoute
import com.lbnkosi.weatherapp.features.screens.home.navigation.homeScreen
import com.lbnkosi.weatherapp.features.screens.places.navigation.placesScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph() {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    WeatherAppScaffold(
        bottomBar = {
            BottomNav.values().forEach { navItem ->
                if (navItem.route == currentRoute) {
                    WeatherAppBottomAppBar(navController = navController, currentDestination = currentDestination)
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background,
    ) { innerPadding ->
        AnimatedNavHost(navController = navController, startDestination = homeNavigationRoute, modifier = Modifier.padding(innerPadding)) {
            homeScreen()
            placesScreen()
        }
    }
}