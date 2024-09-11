package com.lbnkosi.weatherapp.domain.viewstate.places

import androidx.compose.runtime.Stable
import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.domain.viewstate.IViewState

@Stable
data class PlacesViewState(val isLoading: Boolean = false, val places: List<Place>? = arrayListOf()): IViewState