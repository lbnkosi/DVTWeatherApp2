package com.lbnkosi.weatherapp.domain.viewstate.splash

import androidx.compose.runtime.Stable
import com.lbnkosi.weatherapp.domain.viewstate.IViewState

@Stable
data class SplashViewState(
    val isLoading: Boolean = false
) : IViewState