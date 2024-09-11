package com.lbnkosi.weatherapp.domain.usecase

import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.domain.repository.IPlacesRepository
import javax.inject.Inject

class PlacesUseCase @Inject constructor(private val repository: IPlacesRepository) {

    suspend fun getPlaces() = repository.getPlaces()

    suspend fun getPlaceByAddress(address: String) = repository.getPlaceByAddress(address)

    suspend fun createPlace(place: Place) = repository.createPlace(place)

    suspend fun updatePlace(place: Place) = repository.updatePlace(place)

    suspend fun deleteAllPlaces() = repository.deleteAllPlaces()

    suspend fun deletePlace(place: Place) = repository.deletePlace(place)

    suspend fun deletePlaces(places: ArrayList<Place>) = repository.deletePlaces(places)
    
}