package dev.vishalgaur.prefixerapp.network.api

import dev.vishalgaur.prefixerapp.models.CitySearchData
import retrofit2.http.GET
import retrofit2.http.Query

interface CitySearchService {

    companion object {
        const val QUERY_NAME = "name"
    }

    @GET("v1/city")
    suspend fun searchCity(
        @Query(QUERY_NAME) query: String,
    ): List<CitySearchData>?
}
