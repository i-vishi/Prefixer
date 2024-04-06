package dev.vishalgaur.prefixerapp.models.cityTemperature

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.simpleweatherapp.network.models.cityTemperature.TemperatureData
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CityTemperatureResponse(
    val id: String,
    @SerializedName("name")
    val cityName: String,
    val main: TemperatureData,
    @SerializedName("cod")
    val responseCode: Int?,
    @SerializedName("dt_txt")
    val date: String,
) : Parcelable
