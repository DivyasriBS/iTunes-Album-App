package com.divyasri.itunes.data.api

import com.divyasri.itunes.data.models.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    fun getitunesSearch(
        @Query("term") term: String
    ): Deferred<SearchResponse>
}