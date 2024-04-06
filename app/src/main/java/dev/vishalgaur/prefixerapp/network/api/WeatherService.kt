package dev.vishalgaur.prefixerapp.network.api

import dev.vishalgaur.prefixerapp.models.cityForecast.CityForecastResponse
import dev.vishalgaur.prefixerapp.models.cityTemperature.CityTemperatureResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        const val QUERY_Q = "q"
        const val QUERY_UNITS = "units"
        const val UNITS_METRIC = "metric"
    }

    @GET("data/2.5/weather")
    suspend fun getWeatherByCityName(
        @Query(QUERY_Q) cityName: String,
        @Query(QUERY_UNITS) units: String = UNITS_METRIC,
    ): CityTemperatureResponse?

    @GET("data/2.5/forecast")
    suspend fun getCityWeatherForecast(
        @Query(QUERY_Q) cityName: String,
        @Query(QUERY_UNITS) units: String = UNITS_METRIC,
    ): CityForecastResponse?
}
