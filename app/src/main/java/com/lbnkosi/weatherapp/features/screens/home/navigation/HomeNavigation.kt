@file:OptIn(ExperimentalAnimationApi::class)

package com.lbnkosi.weatherapp.features.screens.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.lbnkosi.weatherapp.features.screens.home.HomeScreen
import androidx.hilt.navigation.compose.hiltViewModel

const val homeNavigationRoute = "home_route"

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen() {
    composable(homeNavigationRoute) {
        HomeScreen(hiltViewModel())
    }
}