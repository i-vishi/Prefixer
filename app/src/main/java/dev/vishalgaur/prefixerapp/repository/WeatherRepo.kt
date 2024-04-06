package dev.vishalgaur.prefixerapp.repository

import dev.vishalgaur.prefixerapp.core.base.Result
import dev.vishalgaur.prefixerapp.models.cityForecast.ForecastItem
import dev.vishalgaur.prefixerapp.models.cityTemperature.CityTemperatureResponse

interface WeatherRepo {

    suspend fun getCurrentWeatherDataByCityName(cityName: String): Result<CityTemperatureResponse>

    suspend fun getCityWeatherForecast(cityName: String, limit: Int = 4): Result<List<ForecastItem>>
}
