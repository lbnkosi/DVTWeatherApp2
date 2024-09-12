package com.lbnkosi.weatherapp.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

abstract class BaseRemoteRepository {
    suspend inline fun <T> safeApiCallNoCache(crossinline callFunction: suspend () -> T): Flow<Resource<T>?>? {
        return try {
            val myObject = withContext(Dispatchers.IO) { callFunction.invoke() }
            flow { emit(Resource.success(myObject)) }
        } catch (e: Exception) {
            flow { emit(Resource.error(kotlin.Pair("No weather data available", null), null)) }
        }
    }
}