package com.lbnkosi.weatherapp.domain.repository

import com.lbnkosi.weatherapp.domain.models.places.Place
import kotlinx.coroutines.flow.Flow

interface IPlacesRepository {
    suspend fun getPlaces(): Flow<ArrayList<Place>>
    suspend fun getPlaceByAddress(address: String): Flow<Place?>
    suspend fun createPlace(place: Place): Flow<ArrayList<Place>>
    suspend fun updatePlace(place: Place): Flow<ArrayList<Place>>
    suspend fun deleteAllPlaces(): Flow<ArrayList<Place>>
    suspend fun deletePlace(place: Place): Flow<ArrayList<Place>>
    suspend fun deletePlaces(places: ArrayList<Place>)
}