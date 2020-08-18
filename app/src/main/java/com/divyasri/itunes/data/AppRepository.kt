package com.divyasri.itunes.data

import com.divyasri.itunes.data.api.ApiService
import com.divyasri.itunes.data.models.ErrorResponse
import com.divyasri.itunes.data.models.ResultWrapper
import com.divyasri.itunes.data.models.SearchResponse
import com.google.gson.Gson
import com.tw.gojek.data.preference.PreferenceHelper
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import java.io.IOException

open class AppRepository(
    var apiService: ApiService
) {

    suspend fun getItunesSearch(request: String):
            ResultWrapper<SearchResponse> {
        return makeApiCalls(apiService.getitunesSearch(request))
    }

    private suspend fun <T> makeApiCalls(apiCall: Deferred<T>): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(apiCall.await())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError(throwable)
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }

    private fun convertErrorBody(exception: HttpException): ErrorResponse? {
        return try {
            Gson().fromJson(
                exception.response().errorBody()?.charStream(),
                ErrorResponse::class.java
            )
        } catch (exception: NullPointerException) {
            null
        }
    }

}