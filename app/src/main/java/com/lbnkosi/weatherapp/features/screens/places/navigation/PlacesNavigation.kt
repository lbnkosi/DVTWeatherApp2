@file:OptIn(ExperimentalAnimationApi::class)

package com.lbnkosi.weatherapp.features.screens.places.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.lbnkosi.weatherapp.features.screens.places.PlacesScreen

const val placesNavigationRoute = "places_route"

fun NavController.navigatePlaces(navOptions: NavOptions? = null) {
    this.navigate(placesNavigationRoute, navOptions)
}

fun NavGraphBuilder.placesScreen() {
    composable(placesNavigationRoute) {
        PlacesScreen(hiltViewModel())
    }
}