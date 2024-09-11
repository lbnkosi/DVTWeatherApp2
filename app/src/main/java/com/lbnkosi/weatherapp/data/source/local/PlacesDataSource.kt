package com.lbnkosi.weatherapp.data.source.local

import com.lbnkosi.weatherapp.data.db.places.PlacesDao
import com.lbnkosi.weatherapp.data.entity.PlacesEntity
import com.lbnkosi.weatherapp.data.mappers.map
import com.lbnkosi.weatherapp.data.mappers.mapPlaces
import com.lbnkosi.weatherapp.domain.models.places.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlacesDataSource @Inject constructor(private val placeDao: PlacesDao) {

    /**
     * Get all places from the db
     */
    suspend fun getPlaces(): Flow<ArrayList<PlacesEntity>> {
        val places = placeDao.getPlaces() as ArrayList<PlacesEntity>
        return flow { emit(places) }
    }

    /**
     * Get a place by address
     */
    suspend fun getPlaceByAddress(address: String): Flow<PlacesEntity?> {
        val place = placeDao.getPlaceByAddress(address)
        return flow { emit(place) }
    }

    /**
     * Create and store place to the db
     */
    suspend fun createPlace(place: Place): Flow<ArrayList<PlacesEntity>> {
        placeDao.createPlace(place.map())
        return flow { emit(placeDao.getPlaces() as ArrayList<PlacesEntity>) }
    }

    /**
     * Get place from db and update it
     */
    suspend fun updatePlace(place: Place): Flow<ArrayList<PlacesEntity>> {
        placeDao.updatePlace(place.map())
        return flow { emit(placeDao.getPlaces() as ArrayList<PlacesEntity>) }
    }

    /**
     * Delete a place with a matching id
     */
    suspend fun deletePlace(place: Place): Flow<ArrayList<PlacesEntity>> {
        placeDao.deletePlace(place.id)
        return flow { emit(placeDao.getPlaces() as ArrayList<PlacesEntity>) }
    }

    /**
     * Delete all the places in the db
     */
    suspend fun deleteAllPlaces(): Flow<ArrayList<PlacesEntity>> {
        placeDao.deleteAllPlaces()
        return flow { emit(placeDao.getPlaces() as ArrayList<PlacesEntity>) }
    }

    suspend fun deletePlaces(places: ArrayList<Place>) {
        placeDao.deletePlaces(places.mapPlaces())
    }
    
}