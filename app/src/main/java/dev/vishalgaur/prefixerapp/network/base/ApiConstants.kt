package dev.vishalgaur.prefixerapp.network.base

import dev.vishalgaur.prefixerapp.BuildConfig

object ApiConstants {
    /**
     Current
     https://api.openweathermap.org/data/2.5/weather?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40

     Forecast
     https://api.openweathermap.org/data/2.5/forecast?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40&units=metric
     */
    const val WEATHER_BASE_URL = BuildConfig.WEATHER_BASE_URL
    const val WEATHER_APP_ID = BuildConfig.WEATHER_APP_ID
    const val QUERY_APP_ID = "APPID"

    const val CITY_API_BASE_URL = BuildConfig.CITY_BASE_URL
    const val CITY_API_KEY = BuildConfig.CITY_API_KEY
    const val HEADER_KEY_CITY_API = "X-Api-Key"
}
