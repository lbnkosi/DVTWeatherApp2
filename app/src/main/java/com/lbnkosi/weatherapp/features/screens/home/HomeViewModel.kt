package com.lbnkosi.weatherapp.features.screens.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lbnkosi.weatherapp.domain.enums.ResourceStatus.ERROR
import com.lbnkosi.weatherapp.domain.enums.ResourceStatus.LOADING
import com.lbnkosi.weatherapp.domain.enums.ResourceStatus.SUCCESS
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import com.lbnkosi.weatherapp.domain.usecase.WeatherDataUseCase
import com.lbnkosi.weatherapp.domain.viewstate.IViewEvent
import com.lbnkosi.weatherapp.domain.viewstate.home.HomeViewState
import com.lbnkosi.weatherapp.features.base.BaseViewModel
import com.lbnkosi.weatherapp.features.utils.mapResponseToWeatherData
import com.lbnkosi.weatherapp.location.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: WeatherDataUseCase, @ApplicationContext private val context: Context) : BaseViewModel<HomeViewState, HomeViewEvent>() {

    private val locationProvider = LocationProvider(context)

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            try {
                val location = locationProvider.getCurrentLocation()
                if (location != null) {
                    setState { currentState.copy(isLoading = true) }
                    useCase.getWeatherData(location.first, location.second)?.collect {
                        if (it?.resourceStatus == SUCCESS) {
                            val weatherData = it.data?.mapResponseToWeatherData(location.first, location.second)
                            cache(weatherData)
                            setState { currentState.copy(isLoading = false, data = weatherData) }
                        }
                        if (it?.resourceStatus == LOADING) {
                            setState { currentState.copy(isLoading = true) }
                        }
                        if (it?.resourceStatus == ERROR) {
                            fetchCache(it.message?.first)
                        }
                    }
                } else {
                    setEvent(HomeViewEvent.SnackBarError("No location found"))
                }
            } catch (e: Exception) {
                setEvent(HomeViewEvent.SnackBarError(e.message))
            }
        }
    }

    private suspend fun cache(weatherData: WeatherData?) {
        useCase.deleteCachedWeatherData()
        useCase.createCacheWeatherData(LocalWeatherData(0, weatherData))
    }

    private suspend fun fetchCache(message: String?) {
        viewModelScope.launch {
            useCase.getCachedWeatherData().collect {
                if (it != null){
                    setState { currentState.copy(isLoading = false, data = it.weatherData.apply { this?.usingCache = true }) }
                } else {
                    setEvent(HomeViewEvent.SnackBarError(message))
                }
            }
        }
    }

    override fun createInitialState(): HomeViewState = HomeViewState()
    override fun onTriggerEvent(event: HomeViewEvent) {}

}

sealed class HomeViewEvent : IViewEvent {
    class SnackBarError(val message: String?) : HomeViewEvent()
}