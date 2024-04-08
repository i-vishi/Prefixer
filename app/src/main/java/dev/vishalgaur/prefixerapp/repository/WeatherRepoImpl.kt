package dev.vishalgaur.prefixerapp.repository

import com.example.simpleweatherapp.network.models.cityTemperature.TemperatureData
import dev.vishalgaur.prefixerapp.core.base.Result
import dev.vishalgaur.prefixerapp.core.utils.DateUtils
import dev.vishalgaur.prefixerapp.models.cityForecast.CityForecastResponse
import dev.vishalgaur.prefixerapp.models.cityForecast.ForecastItem
import dev.vishalgaur.prefixerapp.models.cityTemperature.CityTemperatureResponse
import dev.vishalgaur.prefixerapp.network.RetrofitInstance
import dev.vishalgaur.prefixerapp.network.api.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class WeatherRepoImpl : WeatherRepo {

    private val service: WeatherService by lazy { RetrofitInstance.getInstance().create(WeatherService::class.java) }

    override suspend fun getCurrentWeatherDataByCityName(cityName: String): Result<CityTemperatureResponse> {
        return withContext(Dispatchers.IO) {
            val deferredRes = async {
                service.getWeatherByCityName(cityName)
            }
            val response = deferredRes.await()
            if (response != null && response.responseCode == 200) {
                Result.Success(response)
            } else {
                Result.Error(Exception("Some Error Occurred"))
            }
        }
    }

    override suspend fun getCityWeatherForecast(cityName: String, limit: Int): Result<List<ForecastItem>> {
        return withContext(Dispatchers.IO) {
            val deferredRes = async {
                service.getCityWeatherForecast(cityName)
            }
            val response = deferredRes.await()
            if (response != null && response.responseCode == 200) {
                try {
                    val forecastList = formatForecastDataToGetForecastFor4Days(response).toMutableList()
                    // remove current data (first item) from list and limit to 4
                    forecastList.removeFirst()
                    Result.Success(forecastList.take(4))
                } catch (e: Exception) {
                    Result.Error(e)
                }
            } else {
                Result.Error(Exception("Some Error Occurred"))
            }
        }
    }

    private fun formatForecastDataToGetForecastFor4Days(data: CityForecastResponse): List<ForecastItem> {
        val forecastList = mutableListOf<ForecastItem>()
        data.list?.groupBy { cityTemp ->
            val date = DateUtils.parseDateString(cityTemp.date)
            val groupDate: String = date?.let {
                DateUtils.formatDateToAFormat(it, DateUtils.FORMAT_DATE_YYYY_MM_DD)
                    ?: IllegalArgumentException("Invalid date found!")
            }.toString()
            groupDate
        }?.forEach { date, groupList ->
            val avgTemp = groupList.sumOf { it.main.temp } / groupList.size
            val avgMinTemp = groupList.sumOf { it.main.minTemp } / groupList.size
            val avgMaxTemp = groupList.sumOf { it.main.maxTemp } / groupList.size
            val avgForecast = ForecastItem(
                timeStamp = groupList[0].timeStamp,
                main = TemperatureData(avgTemp, avgMinTemp, avgMaxTemp),
                date = date,
            )
            forecastList.add(avgForecast)
        }
        return forecastList
    }
}
