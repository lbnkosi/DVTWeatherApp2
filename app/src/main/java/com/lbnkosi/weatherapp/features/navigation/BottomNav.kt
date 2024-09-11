package com.lbnkosi.weatherapp.features.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lbnkosi.weatherapp.R
import com.lbnkosi.weatherapp.features.screens.home.navigation.homeNavigationRoute
import com.lbnkosi.weatherapp.features.screens.places.navigation.placesNavigationRoute

enum class BottomNav(val route: String, @DrawableRes val iconId: Int, @StringRes val titleTextId: Int) {
    HOME(homeNavigationRoute, R.drawable.round_home_24, R.string.home),
    PLACES(placesNavigationRoute, R.drawable.baseline_place_24, R.string.places)
}