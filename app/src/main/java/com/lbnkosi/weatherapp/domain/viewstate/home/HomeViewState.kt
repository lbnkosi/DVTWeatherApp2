package com.lbnkosi.weatherapp.domain.viewstate.home

import androidx.compose.runtime.Stable
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import com.lbnkosi.weatherapp.domain.viewstate.IViewState

@Stable
data class HomeViewState(val isLoading: Boolean = false, val data: WeatherData? = WeatherData()): IViewState