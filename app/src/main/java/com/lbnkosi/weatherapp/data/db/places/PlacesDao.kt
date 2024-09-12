package com.lbnkosi.weatherapp.data.db.places

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lbnkosi.weatherapp.data.entity.PlacesEntity

@Dao
interface PlacesDao {

    @Query("SELECT * FROM Places")
    suspend fun getPlaces(): List<PlacesEntity>

    //Get place
    @Query("SELECT * FROM Places WHERE id=:id")
    suspend fun getPlace(id: Int): PlacesEntity

    //Get place by id
    @Query("SELECT * FROM Places WHERE id=:id ORDER BY id DESC LIMIT 1")
    suspend fun getPlaceById(id: String): PlacesEntity?

    //Create place
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPlace(placeEntity: PlacesEntity)

    //Update place
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlace(placeEntity: PlacesEntity)

    //Delete all places
    @Query("DELETE FROM Places")
    suspend fun deleteAllPlaces()

    //Delete place
    @Query("DELETE FROM Places WHERE id=:id")
    suspend fun deletePlace(id: Int)

    @Delete
    suspend fun deletePlaces(places: List<PlacesEntity>)
    
}