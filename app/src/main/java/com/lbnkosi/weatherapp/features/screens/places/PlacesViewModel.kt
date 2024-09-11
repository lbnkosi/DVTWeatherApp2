package com.lbnkosi.weatherapp.features.screens.places

import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.domain.usecase.MapsUseCase
import com.lbnkosi.weatherapp.domain.usecase.PlacesUseCase
import com.lbnkosi.weatherapp.domain.viewstate.IViewEvent
import com.lbnkosi.weatherapp.domain.viewstate.places.PlacesViewState
import com.lbnkosi.weatherapp.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val placesUseCase: PlacesUseCase, private val mapsUseCase: MapsUseCase): BaseViewModel<PlacesViewState, PlacesViewEvent>() {

    init {
        getPlaces()
    }

    private fun getPlaces() {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            placesUseCase.getPlaces().collect {
                if (it.isNotEmpty()) {
                    setState { currentState.copy(isLoading = false, places = it) }
                }
                if (it.isEmpty()) {
                    setEvent(PlacesViewEvent.SnackBarError("No Places Stored"))
                    setState { currentState.copy(isLoading = false, places = null) }
                }
            }
        }
    }

    fun saveSelectedPlace(place: Place) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            placesUseCase.createPlace(place).collect {
                setEvent(PlacesViewEvent.SnackBarSuccess("Place Stored"))
                setState { currentState.copy(isLoading = false, places = it) }
            }
        }
    }

    suspend fun getAutocompletePredictions(query: String): List<AutocompletePrediction>? {
        return withContext(Dispatchers.IO) {
            mapsUseCase.getAutocompletePredictions(query)
        }
    }

    suspend fun getMapsPlace(placeId: String): Place? {
        return withContext(Dispatchers.IO) {
            mapsUseCase.getMapsPlace(placeId)
        }
    }

    override fun createInitialState(): PlacesViewState  = PlacesViewState()

    override fun onTriggerEvent(event: PlacesViewEvent) {

    }
}

sealed class PlacesViewEvent: IViewEvent {
    class SnackBarError(val message: String?): PlacesViewEvent()
    class SnackBarSuccess(val message: String?): PlacesViewEvent()
}