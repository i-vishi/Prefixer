package dev.vishalgaur.prefixerapp.network.base

import dev.vishalgaur.prefixerapp.BuildConfig

object ApiConstants {
    /**
     Current
     https://api.openweathermap.org/data/2.5/weather?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40

     Forecast
     https://api.openweathermap.org/data/2.5/forecast?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40&units=metric
     */
    const val BASE_URL = BuildConfig.BASE_URL
    const val WEATHER_APP_ID = BuildConfig.WEATHER_APP_ID
    const val QUERY_APP_ID = "APPID"
}
