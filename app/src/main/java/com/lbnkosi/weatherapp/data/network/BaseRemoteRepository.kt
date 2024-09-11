package com.lbnkosi.weatherapp.data.network

import android.util.Log
import android.util.Pair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRemoteRepository {

    companion object {
        private const val TAG = "BaseRemoteRepository"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    /**
     * Function that executes the given function on Dispatchers.IO context and switch to Dispatchers.Main context when an error occurs
     * @param callFunction is the function that is returning the wanted object. It must be a suspend function. Eg:
     * override suspend fun loginUser(body: LoginUserBody, emitter: RemoteErrorEmitter): LoginUserResponse?  = safeApiCall( { authApi.loginUser(body)} , emitter)
     * @param emitter is the interface that handles the error messages. The error messages must be displayed on the MainThread, or else they would throw an Exception.
     */
    suspend inline fun <T> safeApiCall(fetchCache: Boolean, crossinline callFunction: suspend () -> T, crossinline cacheFunction: suspend () -> T?): Flow<Resource<T>?>? {
        return try {
            val myObject = withContext(Dispatchers.IO) { callFunction.invoke() }
            flow { emit(Resource.success(myObject)) }
        } catch (e: Exception) {
            if (fetchCache) flow {
                val myObject = withContext(Dispatchers.IO) { cacheFunction.invoke() }
                emit(Resource.success(myObject))
            } else flow { emit(Resource.error(kotlin.Pair("No weather data available", null), null)) }
        }
    }

    suspend inline fun <T> safeApiCallNoCache(crossinline callFunction: suspend () -> T): Flow<Resource<T>?>? {
        return try {
            val myObject = withContext(Dispatchers.IO) { callFunction.invoke() }
            flow { emit(Resource.success(myObject)) }
        } catch (e: Exception) {
            flow { emit(Resource.error(kotlin.Pair("No weather data available", null), null)) }
        }
    }

    /**
     * Function that executes the given function in whichever thread is given. Be aware, this is not friendly with Dispatchers.IO,
     * since [RemoteErrorEmitter] is intended to display messages to the user about error from the server/DB.
     * @param callFunction is the function that is returning the wanted object. Eg:
     * override suspend fun loginUser(body: LoginUserBody, emitter: RemoteErrorEmitter): LoginUserResponse?  = safeApiCall( { authApi.loginUser(body)} , emitter)
     * @param emitter is the interface that handles the error messages. The error messages must be displayed on the MainThread, or else they would throw an Exception.
     */
    inline fun <T> safeApiCallNoContext(emitter: RemoteErrorEmitter, callFunction: () -> T): T? {
        return try {
            val myObject = callFunction.invoke()
            myObject
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BaseRemoteRepo", "Call error: ${e.localizedMessage}", e.cause)
            when (e) {
                is HttpException -> {
                    if (e.code() == 401) emitter.onError(e.message, ErrorType.SESSION_EXPIRED)
                    else {
                        val body = e.response()?.errorBody()
                        emitter.onError(getErrorMessage(body))
                    }
                }
                is SocketTimeoutException -> emitter.onError(e.message, ErrorType.TIMEOUT)
                is IOException -> emitter.onError(e.message, ErrorType.NETWORK)
                else -> emitter.onError(e.message, ErrorType.UNKNOWN)
            }
            null
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }
}