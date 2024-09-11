package com.lbnkosi.weatherapp.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import android.location.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationProvider(context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Pair<Double, Double>? {
        return try {
            val location = getLastLocationAsync()
            location?.let {
                Pair(it.latitude, it.longitude)
            }
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocationAsync(): Location? =
        suspendCancellableCoroutine { cont ->
            val task: Task<Location> = fusedLocationClient.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    cont.resume(location)
                } else {
                    cont.resumeWithException(Exception("Location not available"))
                }
            }
            task.addOnFailureListener { exception ->
                cont.resumeWithException(exception)
            }
        }
}