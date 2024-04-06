package dev.vishalgaur.prefixerapp.models.cityForecast

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.simpleweatherapp.network.models.cityTemperature.TemperatureData
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ForecastItem(
    @SerializedName("dt")
    val timeStamp: Long,
    val main: TemperatureData,
    @SerializedName("dt_txt")
    val date: String,
) : Parcelable
