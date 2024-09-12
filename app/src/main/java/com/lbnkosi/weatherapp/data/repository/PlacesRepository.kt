package com.lbnkosi.weatherapp.data.repository

import com.lbnkosi.weatherapp.data.mappers.map
import com.lbnkosi.weatherapp.data.source.local.PlacesDataSource
import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.domain.repository.IPlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val dataSource: PlacesDataSource): IPlacesRepository {
    override suspend fun getPlaces(): Flow<ArrayList<Place>> {
        return dataSource.getPlaces().map { it.map() }
    }

    override suspend fun getPlaceById(id: String): Flow<Place?> {
        return dataSource.getPlaceById(id).map { it?.map() }
    }

    override suspend fun createPlace(place: Place): Flow<ArrayList<Place>> {
        return dataSource.createPlace(place).map { it.map() }
    }

    override suspend fun updatePlace(place: Place): Flow<ArrayList<Place>> {
        return dataSource.updatePlace(place).map { it.map() }
    }

    override suspend fun deleteAllPlaces(): Flow<ArrayList<Place>> {
        return dataSource.deleteAllPlaces().map { it.map() }
    }

    override suspend fun deletePlace(place: Place): Flow<ArrayList<Place>> {
        return dataSource.deletePlace(place).map { it.map() }
    }

    override suspend fun deletePlaces(places: ArrayList<Place>) {
        dataSource.deletePlaces(places)
    }

}