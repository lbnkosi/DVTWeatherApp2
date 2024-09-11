package com.lbnkosi.weatherapp.data.source.remote

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.lbnkosi.weatherapp.data.network.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.lbnkosi.weatherapp.domain.models.places.Place as LocalPlace

class MapsDataSource @Inject constructor(private val placesClient: PlacesClient) {

    suspend fun getAutocompletePredictions(query: String): Resource<List<AutocompletePrediction>> {
        return try {
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build()
            val response = placesClient.findAutocompletePredictions(request).await()
            Resource.success(response.autocompletePredictions)
        } catch (e: Exception) {
            Resource.error(Pair("Error fetching autocomplete predictions", null), emptyList())
        }
    }

    suspend fun getMapsPlace(placeId: String): Resource<LocalPlace> {
        return try {
            val placeFields = listOf(Place.Field.LAT_LNG)
            val request = FetchPlaceRequest.builder(placeId, placeFields).build()
            val response = placesClient.fetchPlace(request).await()

            val place = LocalPlace()
            place.name = response.place.name
            place.description = response.place.address
            place.lat = response?.place?.latLng?.latitude.toString()
            place.long = response?.place?.latLng?.longitude.toString()

            Resource.success(place)
        } catch (e: Exception) {
            Resource.error(Pair("Error fetching place details", null), null)
        }
    }

}