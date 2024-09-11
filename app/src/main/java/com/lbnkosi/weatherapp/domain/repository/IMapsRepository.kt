package com.lbnkosi.weatherapp.domain.repository

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.lbnkosi.weatherapp.domain.models.places.Place
import kotlinx.coroutines.flow.Flow

interface IMapsRepository {
    suspend fun getAutocompletePredictions(query: String): List<AutocompletePrediction>?
    suspend fun getMapsPlace(placeId: String): Place?
}