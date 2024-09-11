package com.lbnkosi.weatherapp.data.repository

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.lbnkosi.weatherapp.data.source.remote.MapsDataSource
import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.domain.repository.IMapsRepository
import javax.inject.Inject

class MapsRepository @Inject constructor(private val dataSource: MapsDataSource): IMapsRepository {
    override suspend fun getAutocompletePredictions(query: String): List<AutocompletePrediction>? {
        return dataSource.getAutocompletePredictions(query).data
    }

    override suspend fun getMapsPlace(placeId: String): Place? {
        return dataSource.getMapsPlace(placeId).data
    }
}