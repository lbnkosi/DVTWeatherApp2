package com.lbnkosi.weatherapp.domain.usecase

import com.lbnkosi.weatherapp.domain.repository.IMapsRepository
import javax.inject.Inject

class MapsUseCase @Inject constructor(private val repository: IMapsRepository) {

    suspend fun getAutocompletePredictions(query: String) = repository.getAutocompletePredictions(query)

    suspend fun getMapsPlace(placeId: String) = repository.getMapsPlace(placeId)

}